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
public class GameStateMessageDistributor implements IGameStateObserver {
	private Set<Integer> aClientIDs; //the set of clients who are participating in a Game instance
	
	/**
	 * Constructor.
	 * @param a set of ClientIDs to be notified of changes to GameState
	 */
	public GameStateMessageDistributor(Set<Integer> pClientIDs) {
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
		AbstractClientMessage lClientMessage = null;
		//TODO
		
		distributeMessage(lClientMessage);
	}
	
	/**
	 * Invokes sendMessage on each client in the set aClientIDs. 
	 * @param pClientMessage
	 * @return void
	 */
	private void distributeMessage(AbstractClientMessage pClientMessage){
		for(ClientOnServer lClientOnServer : ClientManager.getInstance().getSet(aClientIDs)){
			lClientOnServer.sendMessage(pClientMessage);
		}
	}
}
