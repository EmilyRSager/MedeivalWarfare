/**
 * @author Charlie Bloomfield
 * Mar 11, 2015
 */

package mw.server.gamelogic.partitioners;

import mw.client.model.GameMap;

/**
 * 
 */
public abstract class AbstractMapPartitioner {

	protected GameMap aGameMap;
	/**
	 * 
	 */
	public AbstractMapPartitioner(GameMap pGameMap) {
		aGameMap = pGameMap;
	}
	
	public abstract void partition();
}
