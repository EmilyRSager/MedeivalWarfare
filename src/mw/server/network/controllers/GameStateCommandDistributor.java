/**
 * @author Charlie Bloomfield
 * Feb 21, 2015
 */

package mw.server.network.controllers;

import java.util.Collection;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;
import java.util.UUID;

import com.sun.org.apache.xerces.internal.parsers.IntegratedParserConfiguration;

import mw.server.gamelogic.state.Game;
import mw.server.gamelogic.state.Tile;
import mw.server.gamelogic.state.Village;
import mw.server.network.communication.ClientChannel;
import mw.server.network.communication.ClientCommunicationController;
import mw.server.network.mappers.AccountMapper;
import mw.server.network.mappers.ClientChannelMapper;
import mw.server.network.translators.SharedTileTranslator;
import mw.shared.Coordinates;
import mw.shared.SharedTile;
import mw.shared.clientcommands.AbstractClientCommand;
import mw.shared.clientcommands.NewGameCommand;
import mw.shared.clientcommands.UpdateAggregateTilesCommand;
import mw.shared.clientcommands.UpdateTileCommand;

/**
 * Distributes GameState updates to the clients participating with an instance of a single game. 
 * There will be one instance of this class observing each game that exists on the server.
 */
public class GameStateCommandDistributor implements Observer {
	
	private Set<UUID> aAccountIDs; //the set of clients who are participating in a Game instance
	
	/**
	 * As of right now, this class passes a Game object to the SharedTileTranslator, which passes
	 * the Game to the TileController in the getWood and getGold methods. This seems like pretty
	 * bad work flow, but it's necessary as SharedTiles need Gold and Wood, 
	 */
	private Game aGame;
	
	private HashMap<Coordinates, SharedTile> modifiedTilesBuffer;

	/**
	 * Constructor.
	 * @param a set of AccountIDs to be notified of changes to GameState
	 */
	public GameStateCommandDistributor(Set<UUID> pAccountIDs, Game pGame) {
		aAccountIDs = pAccountIDs;
		aGame = pGame;
		modifiedTilesBuffer = new HashMap<Coordinates, SharedTile>();
	}

	/**
	 * Sends changed information about pTile to the set of clients involved in this game instance
	 * @param pSharedTile, the tile whose state has changed in the Model
	 */
	@Override
	public void update(Observable pObservable, Object pObject) {
//		if(pObservable instanceof Tile){
//			
//		}
//		else if(pObservable instanceof Village){
//			//TODO
//		}
//		else{
//			//TODO
//		}
		
		Tile lTile = (Tile) pObservable;
		currentlyActiveDistributor = this;
		SharedTile tileTranslation = SharedTileTranslator.translateTile(lTile, aGame);
		modifiedTilesBuffer.put(tileTranslation.getCoordinates(), tileTranslation);
		//AbstractClientCommand lClientCommand = new UpdateTileCommand(SharedTileTranslator.translateTile(lTile, aGame));
		//distributeCommand(lClientCommand);
	}

	/**
	 * Sends new game information about pGameMap to the set of clients involved in this game instance
	 * @param pGameMap
	 */
	public void newGame(Tile[][] pGameMap) {
		SharedTile[][] lSharedTiles = SharedTileTranslator.translateMap(pGameMap, aGame);
		AbstractClientCommand lClientCommand = new NewGameCommand(lSharedTiles);
		distributeCommand(lClientCommand);
	}
	
	/**
	 * Distributes the changes to each tile in an aggregate update tile state command
	 */
	private void distributeAllCommands()
	{
		Collection<SharedTile> newTileStates = modifiedTilesBuffer.values();
		distributeCommand(new UpdateAggregateTilesCommand(newTileStates));
		modifiedTilesBuffer = new HashMap<Coordinates, SharedTile>();
	}
	
	/**
	 * Invokes sendCommand on each client in the set aAccountIDs. 
	 * @param pClientMessage
	 */
	private void distributeCommand(AbstractClientCommand pClientCommand) {
		for(UUID lUUID : aAccountIDs){
			ClientCommunicationController.sendCommand(lUUID, pClientCommand);
		}
	}
	
	
	/**
	 * @param pAccountIDs
	 * @return
	 */
	private UUID extractRandom(Set<UUID> pAccountIDs){
		return pAccountIDs.iterator().next();
	}
	
	
	/*
	 * Static 
	 */
	
	private static GameStateCommandDistributor currentlyActiveDistributor;
	
	public static void flushBuffer()
	{
		if (currentlyActiveDistributor != null) {
			currentlyActiveDistributor.distributeAllCommands();
			currentlyActiveDistributor = null;
		}
	}
}
