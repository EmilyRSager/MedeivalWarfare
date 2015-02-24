/**
 * @author Charlie Bloomfield
 * Feb 21, 2015
 */

package mw.server.network;

import mw.shared.SharedTile;

/**
 * Defines the functionality that a GameStateObserver must provide. When Game moves
 * reach the server and are executed in the model, GameStateObservers will be informed of the state 
 * of Tiles whose values have changed. 
 */
public interface IGameStateObserver {
	
	/**
	 * Updates the state of a SharedTile in the view.
	 * @param pSharedTile
	 */
	void updateTile(SharedTile pSharedTile);
	
}
