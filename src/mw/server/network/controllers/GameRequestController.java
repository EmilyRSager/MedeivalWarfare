/**
 * @author Charlie Bloomfield
 * Mar 5, 2015
 */

package mw.server.network.controllers;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

import mw.server.gamelogic.Game;
import mw.server.gamelogic.GameController;
import mw.server.gamelogic.Player;
import mw.server.gamelogic.Tile;
import mw.server.gamelogic.TooManyPlayersException;
import mw.server.network.lobby.GameLobby;
import mw.server.network.mappers.ClientChannelMapper;
import mw.server.network.mappers.GameMapper;
import mw.server.network.mappers.PlayerMapper;
import mw.shared.clientcommands.AbstractClientCommand;
import mw.shared.clientcommands.AcknowledgementCommand;

/**
 * Manages game requests by maintaining a set of game lobbies and creating games when there
 * are sufficient clients available to create a Game.
 */
public class GameRequestController {
	private GameLobby aGameLobby; //later on there will be a set of available of
	private static GameRequestController aGameRequestController;

	/**
	 * Constructor
	 */
	private GameRequestController(){
		aGameLobby = new GameLobby();
	}

	/**
	 * Singleton implementation
	 * @return static GameRequestController instance
	 */
	public static GameRequestController getInstance(){
		if(aGameRequestController == null){
			aGameRequestController = new GameRequestController();
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

		//get the available clients
		System.out.println("[Server] Initializing new game.");
		Set<Integer> lClientIDs = aGameLobby.removeAvailableClients();
		int lNumPlayers = lClientIDs.size();
		
		System.out.println("[Server] Number clients " + lNumPlayers + ".");

		//create a game
		Game lGame;
		try {
			System.out.println("[Server] Making new game request to GameController.");
			lGame = GameController.newGame(lNumPlayers); //throws exception if too many players
			
			System.out.println("[Server] Received game from GameController.");
			
			/* Map the clients to the given Game.
			 * TODO this may be unnesecary as there will be a mapping between ClientIDs and Players as well
			 */
			GameMapper.getInstance().putGame(lClientIDs, lGame); //add clients to Game Mapping
			
			//map clients to players
			Collection<Player> lPlayers = lGame.getPlayers();
			assignPlayers(lClientIDs, lPlayers);
			
			//initialize game state observer
			GameStateCommandDistributor lGameStateCommandDistributor = 
					new GameStateCommandDistributor(lClientIDs, lGame);
			
			//attach observer to each tile
			Tile[][] lGameTiles = lGame.getGameTiles();
			for(int i = 0; i < lGameTiles.length; i++){
				for(int j = 0; j < lGameTiles[0].length; j++){
					//add observer to each tile
					lGameTiles[i][j].addObserver(lGameStateCommandDistributor);
				}
			}
			
			//distribute the new Game to each client.
			lGameStateCommandDistributor.newGame(lGame.getGameTiles());
			
		} catch (TooManyPlayersException e) {
			System.out.println("[Server] Tried to create a Game with too many players.");
			e.printStackTrace();
		}

	}

	/**
	 * Assigns a Client to a given players in the Game
	 * @param pPlayers
	 * @param pClientIDs
	 */
	private void assignPlayers(Set<Integer> pClientIDs, Collection<Player> pPlayers){
		Iterator<Integer> lClientIDIterator = pClientIDs.iterator();
		Iterator<Player> lPlayerIterator = pPlayers.iterator();

		//TODO check they're the same size
		while(lClientIDIterator.hasNext()){
			PlayerMapper.getInstance().putPlayer(
					lClientIDIterator.next(),
					lPlayerIterator.next()
					);
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