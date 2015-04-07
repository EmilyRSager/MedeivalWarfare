/**
 * @author Charlie Bloomfield
 * Mar 5, 2015
 */

package mw.server.network.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import java.util.UUID;

import test.mw.server.gamelogic.SaveGame;
import mw.server.admin.Account;
import mw.server.admin.AccountGameInfo;
import mw.server.admin.AccountManager;
import mw.server.gamelogic.controllers.GameController;
import mw.server.gamelogic.enums.Color;
import mw.server.gamelogic.exceptions.TooManyPlayersException;
import mw.server.gamelogic.state.Game;
import mw.server.gamelogic.state.GameID;
import mw.server.gamelogic.state.Player;
import mw.server.gamelogic.state.Tile;
import mw.server.network.communication.ClientCommunicationController;
import mw.server.network.lobby.GameLobby;
import mw.server.network.lobby.LoadableGameRoom;
import mw.server.network.mappers.GameMapper;
import mw.server.network.mappers.PlayerMapper;
import mw.server.network.translators.LobbyTranslator;
import mw.server.network.translators.SharedTileTranslator;
import mw.shared.SharedGameLobby;
import mw.shared.clientcommands.AbstractClientCommand;
import mw.shared.clientcommands.AcknowledgementCommand;
import mw.shared.clientcommands.DisplayGameLobbyCommand;
import mw.shared.clientcommands.NotifyBeginTurnCommand;
import mw.shared.clientcommands.SetColorCommand;
import mw.util.MultiArrayIterable;
import mw.util.Tuple2;

/**
 * Manages game requests by maintaining a set of game lobbies and creating games when there
 * are sufficient clients available to create a Game. Handles assigning clients to GamePlayers
 * and informing the clients of their Colors.
 */
public class GameInitializationController {
	private static GameInitializationController aGameInitializationController;
	private GameLobby aGameLobby;
	
	/**
	 * Constructor
	 */
	private GameInitializationController(){
		aGameLobby = new GameLobby();
	}

	/**
	 * Singleton implementation
	 * @return static GameInitializationController instance
	 */
	public static GameInitializationController getInstance(){
		if(aGameInitializationController == null){
			aGameInitializationController = new GameInitializationController();
		}

		return aGameInitializationController;
	}
	
	/**
	 * gets the loaded game, finds out the allowable Account UUIDs and then creates a LoadableGameRoom
	 */
	public void createLoadableGame(UUID pAccountUUID, GameID pGameID){
		Game lGame = pGameID.getaGame();
		int lNumRequestedClients = lGame.getPlayers().size();
		
		ArrayList<Player> list = (ArrayList<Player>) lGame.getPlayers();
		ArrayList<UUID> listOfAccountUUIDs = new ArrayList<UUID>();
		for(Player p: list){
			listOfAccountUUIDs.add(PlayerMapper.getInstance().getAccount(p));
		}
		
		LoadableGameRoom lLoadableGameRoom = new LoadableGameRoom(lNumRequestedClients, listOfAccountUUIDs);
		
		//TODO: inform all the clients that they need to join this game
		//TODO: inform this client with a new LoadableGameRoom
		
//		ClientCommunicationController.sendCommand(pAccountUUID, new Displa);
	}
	
	/**
	 * @return a set of game lobbies that are open and waiting for players to join
	 */
	public void getJoinableGames(UUID pRequestingAccountID){
		SharedGameLobby lSharedGameLobby = LobbyTranslator.translateGameLobby(aGameLobby);
		ClientCommunicationController.sendCommand(pRequestingAccountID, new DisplayGameLobbyCommand(lSharedGameLobby));
	}

	/**
	 * Creates a new game if there are sufficient Accounts waiting to play. Otherwise, an 
	 * acknowledgement is sent to the Account informing her to wait.
	 * @param pAccountID
	 */
	public void requestNewGame(UUID pRequestingAccountID, String pGameName, int pNumRequestedPlayers){
		aGameLobby.createNewGameRoom(pGameName, pNumRequestedPlayers);
		aGameLobby.addParticipantToGame(pRequestingAccountID, pGameName);
		ClientCommunicationController.sendCommand(pRequestingAccountID, new AcknowledgementCommand("Game \"" + pGameName + "\" was created. Awaiting other players."));
	}
	
	/**
	 * @param pAccountID
	 * @param pGameName
	 */
	public void joinGame(UUID pJoiningAccountID, String pGameName){
		aGameLobby.addParticipantToGame(pJoiningAccountID, pGameName);
		if(aGameLobby.roomIsComplete(pGameName)){
			Set<UUID> lLobbyClients = aGameLobby.getParticipantAccounts(pGameName);
			aGameLobby.removeGameRoom(pGameName);
			createNewGame(lLobbyClients, pGameName);
		}
		else{
			ClientCommunicationController.sendCommand(pJoiningAccountID, new AcknowledgementCommand("Game \"" + pGameName + "\" successfully joined. Awaiting other players"));
		}
	}

	/**
	 * Creates a new game, adds the necessary observers to the Game, and then sends the Game
	 * to each client involved in the game.
	 */
	private void createNewGame(Set<UUID> pAccountIDs, String pGameName){
		System.out.println("[Server] Initializing new game.");
		int lNumPlayers = pAccountIDs.size();

		//create a game
		Game lGame;
		try {
			lGame = GameController.newGame(lNumPlayers); //throws exception if too many players

			/* Map the clients to the given Game.
			 * TODO this may be unnecessary as there will be a mapping between AccountIDs and Players as well
			 */
			GameMapper.getInstance().putGame(pAccountIDs, lGame); //add clients to Game Mapping

			//map clients to players
			Collection<Player> lPlayers = lGame.getPlayers();

			//initialize game state observer
			GameStateCommandDistributor lGameStateCommandDistributor = 
					new GameStateCommandDistributor(pAccountIDs, lGame);

			//attach observer to each tile
			Tile[][] lGameTiles = lGame.getGameTiles();
			for(Tile lTile : MultiArrayIterable.toIterable(lGameTiles)){
				//add observer to each tile
				lTile.addObserver(lGameStateCommandDistributor);
			}

			//distribute the new Game to each client.
			lGameStateCommandDistributor.newGame(lGameTiles);
			assignAccountsToPlayers(pAccountIDs, lPlayers);
			
			for (UUID accountUUID : pAccountIDs) {
				Account lAccount = AccountManager.getInstance().getAccount(accountUUID);
				AccountGameInfo lAccountGameInfo = lAccount.getaAccountGameInfo();
				Color playerColor = PlayerMapper.getInstance().getPlayer(accountUUID).getPlayerColor();
				//TODO: fix the following line for name 
				lAccountGameInfo.setCurrentGame(new Tuple2<String, Color>("", playerColor ));
				lAccountGameInfo.addToActiveGames(lAccountGameInfo.getCurrentGame());
				AccountManager.getInstance().saveAccountData(lAccount);
			}
			GameID lGameID = new GameID(lGame, pGameName, pAccountIDs);
			try {
				SaveGame.SaveMyGame(lGameID);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//Inform client that it is his turn
			UUID lCurrentAccountID = PlayerMapper.getInstance().getAccount(GameController.getCurrentPlayer(lGame));
			ClientCommunicationController.sendCommand(lCurrentAccountID, new NotifyBeginTurnCommand());
			
		} catch (TooManyPlayersException e) {
			System.out.println("[Server] Tried to create a Game with too many players.");
			e.printStackTrace();
		}
	}

	/**
	 * Assigns a AccountID to a given Player in the Game and informs each Account of its color.
	 * @param pPlayers
	 * @param pAccountIDs
	 */
	private void assignAccountsToPlayers(Set<UUID> pAccountIDs, Collection<Player> pPlayers){
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

	/**
	 * Sends an acknowledgement to pAccountID that the game request has been received.
	 * @param pAccountID
	 */
	private void acknowledgeGameRequest(UUID pAccountID){
		AbstractClientCommand lClientCommand =
				new AcknowledgementCommand("Game request received. Insufficient current users. Please wait for more clients to join lobby.");

		ClientCommunicationController.sendCommand(pAccountID, lClientCommand);
	}
	
}
