/**
 * @author Charlie Bloomfield
 * Feb 21, 2015
 */

package mw.server.network;

import mw.shared.SharedTile;

/**
 * This Interface defines the functionality that a GameStateObserver must provide. When Game moves
 * reach the server and are executed in the model, GameStateObservers will be informed of the state 
 * of Tiles whose values have changed. 
 */
public interface IGameStateObserver {
	void updateTile(SharedTile pSharedTile);
	
}
