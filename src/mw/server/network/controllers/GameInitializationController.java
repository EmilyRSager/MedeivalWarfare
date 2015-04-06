/**
 * @author Charlie Bloomfield
 * Mar 5, 2015
 */

package mw.server.network.controllers;

import java.io.IOException;
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
import mw.server.gamelogic.state.Player;
import mw.server.gamelogic.state.Tile;
import mw.server.network.communication.ClientChannel;
import mw.server.network.communication.ClientCommunicationController;
import mw.server.network.lobby.GameLobby;
import mw.server.network.mappers.AccountMapper;
import mw.server.network.mappers.ClientChannelMapper;
import mw.server.network.mappers.GameMapper;
import mw.server.network.mappers.PlayerMapper;
import mw.server.network.translators.SharedTileTranslator;
import mw.shared.clientcommands.AbstractClientCommand;
import mw.shared.clientcommands.AcknowledgementCommand;
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
	private GameLobby aGameLobby; //later on there will be a set of available of
	private static GameInitializationController aGameRequestController;

	/**
	 * Constructor
	 */
	private GameInitializationController(){
		aGameLobby = new GameLobby();
	}

	/**
	 * Singleton implementation
	 * @return static GameRequestController instance
	 */
	public static GameInitializationController getInstance(){
		if(aGameRequestController == null){
			aGameRequestController = new GameInitializationController();
		}

		return aGameRequestController;
	}

	/**
	 * Creates a new game if there are sufficient Accounts waiting to play. Otherwise, an 
	 * acknowledgement is sent to the Account informing her to wait.
	 * @param pAccountID
	 */
	public void requestNewGame(UUID pAccountID){
		aGameLobby.addAccount(pAccountID);
		if(aGameLobby.containsSufficientPlayersForGame()){
			createNewGame();
		}

		else{
			acknowledgeGameRequest(pAccountID);
		}
	}

	/**
	 * Creates a new game, adds the necessary observers to the Game, and then sends the Game
	 * to each client involved in the game.
	 */
	private void createNewGame(){
		System.out.println("[Server] Initializing new game.");
		Set<UUID> lAccountIDs = aGameLobby.removeAvailableAccounts();
		int lNumPlayers = lAccountIDs.size();

		//create a game
		Game lGame;
		try {
			lGame = GameController.newGame(lNumPlayers); //throws exception if too many players

			/* Map the clients to the given Game.
			 * TODO this may be unnesecary as there will be a mapping between AccountIDs and Players as well
			 */
			GameMapper.getInstance().putGame(lAccountIDs, lGame); //add clients to Game Mapping

			//map clients to players
			Collection<Player> lPlayers = lGame.getPlayers();

			//initialize game state observer
			GameStateCommandDistributor lGameStateCommandDistributor = 
					new GameStateCommandDistributor(lAccountIDs, lGame);

			//attach observer to each tile
			Tile[][] lGameTiles = lGame.getGameTiles();
			for(Tile lTile : MultiArrayIterable.toIterable(lGameTiles)){
				//add observer to each tile
				lTile.addObserver(lGameStateCommandDistributor);
			}

			//distribute the new Game to each client.
			lGameStateCommandDistributor.newGame(lGameTiles);
			assignAccountsToPlayers(lAccountIDs, lPlayers);
			
			for (UUID accountUUID: lAccountIDs) {
				Account lAccount = AccountManager.getInstance().getAccount(accountUUID);
				AccountGameInfo lAccountGameInfo = lAccount.getaAccountGameInfo();
				Color playerColor = PlayerMapper.getInstance().getPlayer(accountUUID).getPlayerColor();
				lAccountGameInfo.setCurrentGame(new Tuple2<String, Color>(lGame.getName(), playerColor ));
				lAccountGameInfo.addToActiveGames(lAccountGameInfo.getCurrentGame());
				AccountManager.getInstance().saveAccountData(lAccount);
			}
			
			try {
				SaveGame.SaveMyGame(lGame);
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
