/**
 * @author Charlie Bloomfield
 * Mar 11, 2015
 */

package mw.server.gamelogic.partitioners;

import java.util.Collection;

import mw.server.gamelogic.enums.Color;
import mw.server.gamelogic.state.GameMap;


/**
 * 
 */
public abstract class AbstractMapPartitioner {
	protected GameMap aGameMap;
	
	public AbstractMapPartitioner(GameMap pGameMap){
		aGameMap = pGameMap;
	}
	
	abstract void partition(Collection<Color> pColors);
}
