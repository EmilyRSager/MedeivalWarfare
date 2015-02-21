/**
 * @author Charlie Bloomfield
 * Feb 21, 2015
 */

package mw.server.network;

import java.util.Set;

import mw.shared.SharedTile;
import mw.shared.clientmessages.AbstractClientMessage;

/**
 * This class is in charge on distributing Game State updates to the clients
 * participating with an instance of a single game. There will be one instance
 * of this class observing each game that exits on the server.
 */
public class GameStateClientMessageHandler implements IGameStateObserver {
	private Set<Integer> aClientIDs;
	
	/**
	 * Constructor.
	 * @param a set of ClientIDs to be notified of changes to GameState
	 */
	public GameStateClientMessageHandler(Set<Integer> pClientIDs) {
		aClientIDs = pClientIDs;
	}

	/**
	 * Builds an UpdatedTileClientMessage that is forwarded to every client that
	 * is participating 
	 * @param pSharedTile, the tile whose state has changed in the Model
	 * @return void
	 */
	@Override
	public void updateTile(SharedTile pSharedTile) {
		AbstractClientMessage lClientMessage = null;
		//TODO fill in 
		
		distributeMessageToClients(lClientMessage);
	}
	
	/**
	 * Invokes sendMessage on each client in the set aClientIDs. 
	 * @param pClientMessage
	 * @return void
	 */
	private void distributeMessageToClients(AbstractClientMessage pClientMessage){
		ClientManager lClientManager = ClientManager.getInstance();
		for(Integer lClientID : aClientIDs){
			lClientManager.get(lClientID).sendMessage(pClientMessage);
		}
	}
}
