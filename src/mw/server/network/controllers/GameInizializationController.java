/**
 * @author Charlie Bloomfield
 * Mar 5, 2015
 */

package mw.server.network.controllers;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

import mw.server.gamelogic.controllers.GameController;
import mw.server.gamelogic.enums.Color;
import mw.server.gamelogic.exceptions.TooManyPlayersException;
import mw.server.gamelogic.state.Game;
import mw.server.gamelogic.state.Player;
import mw.server.gamelogic.state.Tile;
import mw.server.network.communication.ClientChannel;
import mw.server.network.lobby.GameLobby;
import mw.server.network.mappers.ClientChannelMapper;
import mw.server.network.mappers.GameMapper;
import mw.server.network.mappers.PlayerMapper;
import mw.server.network.translators.SharedTileTranslator;
import mw.shared.clientcommands.AbstractClientCommand;
import mw.shared.clientcommands.AcknowledgementCommand;
import mw.shared.clientcommands.NotifyBeginTurnCommand;
import mw.shared.clientcommands.SetColorCommand;
import mw.util.MultiArrayIterable;

/**
 * Manages game requests by maintaining a set of game lobbies and creating games when there
 * are sufficient clients available to create a Game. Handles assigning clients to GamePlayers
 * and informing the clients of their Colors.
 */
public class GameInizializationController {
	private GameLobby aGameLobby; //later on there will be a set of available of
	private static GameInizializationController aGameRequestController;

	/**
	 * Constructor
	 */
	private GameInizializationController(){
		aGameLobby = new GameLobby();
	}

	/**
	 * Singleton implementation
	 * @return static GameRequestController instance
	 */
	public static GameInizializationController getInstance(){
		if(aGameRequestController == null){
			aGameRequestController = new GameInizializationController();
		}

		return aGameRequestController;
	}

	/**
	 * Creates a new game if there are sufficient Clients waiting to play. Otherwise, an 
	 * acknowledgement is sent to the Client informing her to wait.
	 * @param pClientID
	 */
	public void requestNewGame(Integer pClientID){
		aGameLobby.addClient(pClientID);
		if(aGameLobby.containsSufficientClientsForGame()){
			createNewGame();
		}

		else{
			acknowledgeGameRequest(pClientID);
		}
	}

	/**
	 * Creates a new game, adds the necessary observers to the Game, and then sends the Game
	 * to each client involved in the game.
	 */
	private void createNewGame(){
		System.out.println("[Server] Initializing new game.");
		Set<Integer> lClientIDs = aGameLobby.removeAvailableClients();
		int lNumPlayers = lClientIDs.size();

		//create a game
		Game lGame;
		try {
			lGame = GameController.newGame(lNumPlayers); //throws exception if too many players

			/* Map the clients to the given Game.
			 * TODO this may be unnesecary as there will be a mapping between ClientIDs and Players as well
			 */
			GameMapper.getInstance().putGame(lClientIDs, lGame); //add clients to Game Mapping

			//map clients to players
			Collection<Player> lPlayers = lGame.getPlayers();

			//initialize game state observer
			GameStateCommandDistributor lGameStateCommandDistributor = 
					new GameStateCommandDistributor(lClientIDs, lGame);

			//attach observer to each tile
			Tile[][] lGameTiles = lGame.getGameTiles();
			for(Tile lTile : MultiArrayIterable.toIterable(lGameTiles)){
				//add observer to each tile
				lTile.addObserver(lGameStateCommandDistributor);
			}

			//distribute the new Game to each client.
			lGameStateCommandDistributor.newGame(lGame.getGameTiles());
			assignClientsToPlayers(lClientIDs, lPlayers);

			//Inform client that it is his turn
			Integer lCurrentClientID = PlayerMapper.getInstance().getClient(GameController.getCurrentPlayer(lGame));
			ClientChannelMapper.getInstance().getChannel(lCurrentClientID).sendCommand(new NotifyBeginTurnCommand());
			
		} catch (TooManyPlayersException e) {
			System.out.println("[Server] Tried to create a Game with too many players.");
			e.printStackTrace();
		}
	}

	/**
	 * Assigns a ClientID to a given Player in the Game and informs each Client of its color.
	 * @param pPlayers
	 * @param pClientIDs
	 */
	private void assignClientsToPlayers(Set<Integer> pClientIDs, Collection<Player> pPlayers){
		Iterator<Integer> lClientIDIterator = pClientIDs.iterator();
		Iterator<Player> lPlayerIterator = pPlayers.iterator();

		//TODO check they're the same size
		while(lClientIDIterator.hasNext()){
			Integer lClientID = lClientIDIterator.next();
			Player lPlayer = lPlayerIterator.next();

			//store client to player mapping
			PlayerMapper.getInstance().putPlayer(lClientID, lPlayer);

			//get player color
			Color lPlayerColor = lPlayer.getPlayerColor();

			//get client channel associated with current player
			ClientChannel lClientChannel = ClientChannelMapper.getInstance().getChannel(lClientID);

			//send command to client informing it of its color
			lClientChannel.sendCommand(
					new SetColorCommand(SharedTileTranslator.translateColor(lPlayerColor)));
		}
	}

	/**
	 * Sends an acknowledgement to pClientID that the game request has been received.
	 * @param pClientID
	 */
	private void acknowledgeGameRequest(Integer pClientID){
		AbstractClientCommand lClientCommand =
				new AcknowledgementCommand("Game request received. Insufficient current users. Please wait for more clients to join lobby.");

		//send acknowledgement to clients
		ClientChannelMapper.getInstance().getChannel(pClientID).sendCommand(lClientCommand);
	}
}
