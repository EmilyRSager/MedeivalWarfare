/**
 * @author Charlie Bloomfield
 * Feb 21, 2015
 */

package mw.server.network;

import java.util.Set;

import mw.shared.SharedTile;
import mw.shared.clientcommands.AbstractClientCommand;
import mw.shared.clientcommands.NewGameCommand;
import mw.shared.clientcommands.UpdateTileCommand;

/**
 * Distributes GameState updates to the clients participating with an instance of a single game. 
 * There will be one instance of this class observing each game that exists on the server.
 */
public class GameStateCommandDistributor implements IGameStateObserver {
	private Set<Integer> aClientIDs; //the set of clients who are participating in a Game instance
	
	/**
	 * Constructor.
	 * @param a set of ClientIDs to be notified of changes to GameState
	 */
	public GameStateCommandDistributor(Set<Integer> pClientIDs) {
		aClientIDs = pClientIDs;
	}

	/**
	 * Builds an UpdatedTileClientMessage that is forwarded to every client that
	 * is participating. 
	 * @param pSharedTile, the tile whose state has changed in the Model
	 * @return void
	 */
	@Override
	public void updateTile(SharedTile pSharedTile) {
		AbstractClientCommand lClientCommand = 
				new UpdateTileCommand(pSharedTile);
		
		distributeMessage(lClientCommand);
	}
	
	public void newGame(SharedTile[][] pGameMap){
		AbstractClientCommand lClientCommand = 
				new NewGameCommand(pGameMap);
		
		distributeMessage(lClientCommand);
	}
	
	/**
	 * Invokes sendMessage on each client in the set aClientIDs. 
	 * @param pClientMessage
	 * @return void
	 */
	private void distributeMessage(AbstractClientCommand pClientCommand){
		for(ClientChannel lClientOnServer : ClientChannelManager.getInstance().getChannelSet(aClientIDs)){
			lClientOnServer.sendCommand(pClientCommand);
		}
	}
}
