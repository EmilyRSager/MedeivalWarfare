/**
 * @author Charlie Bloomfield
 * Mar 5, 2015
 */

package mw.server.network.lobby;

import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Observer;
import java.util.Set;
import java.util.UUID;

import mw.server.admin.Account;
import mw.server.admin.AccountGameInfo;
import mw.server.admin.AccountManager;
import mw.server.gamelogic.controllers.GameController;
import mw.server.gamelogic.enums.Color;
import mw.server.gamelogic.exceptions.TooManyPlayersException;
import mw.server.gamelogic.state.Game;
import mw.server.gamelogic.state.Player;
import mw.server.gamelogic.state.Tile;
import mw.server.network.communication.ClientCommunicationController;
import mw.server.network.controllers.GameStateCommandDistributor;
import mw.server.network.mappers.GameMapper;
import mw.server.network.mappers.PlayerMapper;
import mw.server.network.translators.SharedTileTranslator;
import mw.shared.clientcommands.NotifyBeginTurnCommand;
import mw.shared.clientcommands.SetColorCommand;
import mw.util.MultiArrayIterable;
import mw.util.Tuple2;
import test.mw.server.gamelogic.SaveGame;

/**
 * @singleton
 * Maintains a set of users waiting to join a Game, and creates a game when there are enough users to play.
 * For simplicity, the game lobby supports creating games when there are enough available players. Later on,
 * this will be extended to provide functionality for specific GameRequests.
 */
public class GameRoom {
	protected Set<UUID> aWaitingClients;
	protected int aNumRequestedClients;

	/*
	 * Constructor
	 */
	public GameRoom(int pNumRequestedClients){
		aWaitingClients = new HashSet<UUID>();
		aNumRequestedClients = pNumRequestedClients;
	}

	/**
	 * adds client pClient to the waiting queue if she is not already waiting.
	 * @param pClientID
	 * @throws Illegal argument exception if the paramter account is not allowed to join this game room (only applicable for subclasses at this point)
	 */
	public void addClient(UUID pAccountID) throws IllegalArgumentException {
		aWaitingClients.add(pAccountID);
		if(!aWaitingClients.contains(pAccountID)){
			aWaitingClients.add(pAccountID);
		}
	}

	/**
	 * @return true if there are sufficient clients for a game
	 */
	public boolean containsSufficientClientsForGame(){
		return aWaitingClients.size() == aNumRequestedClients;
	}

	/**
	 * @return true if there are no clients in the room
	 */
	public boolean isEmpty(){
		return aWaitingClients.size() == 0;
	}

	/**
	 * @return the a set of Clients at the head of the waiting queue
	 */
	public Set<UUID> getClients(){
		return aWaitingClients;
	}

	/**
	 * Initializes a game and maps the participant accounts to the Game instance and their respective Player instances
	 * @param pGameName
	 * @throws TooManyPlayersException
	 */
	public void initializeGame(String pGameName) throws TooManyPlayersException {
		System.out.println("[Server] Initializing new game.");
		int lNumPlayers = aNumRequestedClients;
		Set<UUID> pAccountIDs = aWaitingClients;
		//create a game
		Game lGame = GameController.newGame(lNumPlayers); //throws exception if too many players
		GameID lGameID = new GameID(lGame, pGameName, pAccountIDs);
		GameMapper.getInstance().putGameID(pAccountIDs, lGameID); //add clients to Game Mapping

		//map clients to players
		GameStateCommandDistributor lGameStateCommandDistributor = 
				new GameStateCommandDistributor(aWaitingClients, lGame);
		attachObservable(lGame, lGameStateCommandDistributor);
		lGameStateCommandDistributor.newGame(lGame.getGameTiles());
		
		//distribute the new Game to each client.
		Collection<Player> lPlayers = lGame.getPlayers();
		assignAccountsToPlayers(pAccountIDs, lPlayers);

		for (UUID accountUUID : pAccountIDs) {
			Account lAccount = AccountManager.getInstance().getAccount(accountUUID);
			AccountGameInfo lAccountGameInfo = lAccount.getAccountGameInfo();
			Color playerColor = PlayerMapper.getInstance().getPlayer(accountUUID).getPlayerColor();
			lAccountGameInfo.setCurrentGame(new Tuple2<String, Color>(pGameName, playerColor));
			lAccountGameInfo.addToActiveGames(lAccountGameInfo.getCurrentGame());
			AccountManager.getInstance().saveAccountData(lAccount);
		}
		
		try {
			SaveGame.SaveMyGame(lGameID);
		} catch (IOException e) {
			System.out.println("[server] Failed to save game \"" + lGameID.getName() + "\".");
			e.printStackTrace();
		}

		//Inform client that it is his turn
		UUID lCurrentAccountID = PlayerMapper.getInstance().getAccount(GameController.getCurrentPlayer(lGame));
		ClientCommunicationController.sendCommand(lCurrentAccountID, new NotifyBeginTurnCommand());
	}

	/**
	 * Assigns a AccountID to a given Player in the Game and informs each Account of its color.
	 * @param pPlayers
	 * @param pAccountIDs
	 */
	protected void assignAccountsToPlayers(Set<UUID> pAccountIDs, Collection<Player> pPlayers){
		Iterator<UUID> lAccountIDIterator = pAccountIDs.iterator();
		Iterator<Player> lPlayerIterator = pPlayers.iterator();

		//TODO check they're the same size
		while(lAccountIDIterator.hasNext()){
			UUID lAccountID = lAccountIDIterator.next();
			Player lPlayer = lPlayerIterator.next();

			//store client to player mapping
			PlayerMapper.getInstance().putPlayer(lAccountID, lPlayer);

			//get player color
			Color lPlayerColor = lPlayer.getPlayerColor();

			ClientCommunicationController.sendCommand(lAccountID, new SetColorCommand(SharedTileTranslator.translateColor(lPlayerColor)));
		}
	}

	protected void attachObservable(Game pGame, Observer pObserver){
		//attach observer to each tile
		Tile[][] lGameTiles = pGame.getGameTiles();
		//initialize game state observer
		for(Tile lTile : MultiArrayIterable.toIterable(lGameTiles)){
			//add observer to each tile
			lTile.addObserver(pObserver);
		}
	}
}
