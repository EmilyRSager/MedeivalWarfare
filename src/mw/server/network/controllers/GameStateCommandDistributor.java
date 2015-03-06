/**
 * @author Charlie Bloomfield
 * Feb 21, 2015
 */

package mw.server.network.controllers;

import java.util.Observable;
import java.util.Observer;
import java.util.Set;

import mw.server.gamelogic.Tile;
import mw.server.network.ClientChannel;
import mw.server.network.SharedTileTranslator;
import mw.server.network.mappers.ClientChannelMapper;
import mw.shared.clientcommands.AbstractClientCommand;
import mw.shared.clientcommands.NewGameCommand;
import mw.shared.clientcommands.UpdateTileCommand;

/**
 * Distributes GameState updates to the clients participating with an instance of a single game. 
 * There will be one instance of this class observing each game that exists on the server.
 */
public class GameStateCommandDistributor implements Observer {
	private Set<Integer> aClientIDs; //the set of clients who are participating in a Game instance
	
	/**
	 * Constructor.
	 * @param a set of ClientIDs to be notified of changes to GameState
	 */
	public GameStateCommandDistributor(Set<Integer> pClientIDs) {
		aClientIDs = pClientIDs;
	}

	/**
	 * Sends changed information about pTile to the set of clients involved in this game instance
	 * @param pSharedTile, the tile whose state has changed in the Model
	 */
	@Override
	public void update(Observable pObservable, Object pObject) {
		Tile lTile = (Tile) pObservable;
		AbstractClientCommand lClientCommand = 
				new UpdateTileCommand(SharedTileTranslator.translateTile(lTile));
		
		distributeMessage(lClientCommand);
	}
	
	/**
	 * Sends new game information about pGameMap to the set of clients involved in this game instance
	 * @param pGameMap
	 */
	public void newGame(Tile[][] pGameMap){
		
		AbstractClientCommand lClientCommand = 
				new NewGameCommand(SharedTileTranslator.translateMap(pGameMap));
		
		distributeMessage(lClientCommand);
	}
	
	/**
	 * Invokes sendCommand on each client in the set aClientIDs. 
	 * @param pClientMessage
	 */
	private void distributeMessage(AbstractClientCommand pClientCommand){
		for(ClientChannel lClientOnServer : ClientChannelMapper.getInstance().getChannelSet(aClientIDs)){
			lClientOnServer.sendCommand(pClientCommand);
		}
	}
}
