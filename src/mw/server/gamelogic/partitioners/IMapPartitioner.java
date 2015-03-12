/**
 * @author Charlie Bloomfield
 * Mar 11, 2015
 */

package mw.server.gamelogic.partitioners;

import java.util.Collection;

import mw.server.gamelogic.model.Color;
import mw.server.gamelogic.model.GameMap;

/**
 * 
 */
public interface IMapPartitioner {
	
	void partition(GameMap pGameMap, Collection<Color> pColors);
}
