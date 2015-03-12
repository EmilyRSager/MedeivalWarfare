/**
 * @author Charlie Bloomfield
 * Mar 12, 2015
 */

package mw.server.gamelogic.partitioners;

import java.util.Collection;

import mw.server.gamelogic.enums.Color;
import mw.server.gamelogic.state.GameMap;

/**
 * 
 */
public class SeaBorderPartitioner extends AbstractMapPartitioner {

	/**
	 * @param pGameMap
	 */
	public SeaBorderPartitioner(GameMap pGameMap) {
		super(pGameMap);
	}

	/**
	 * @see mw.server.gamelogic.partitioners.AbstractMapPartitioner#partition(java.util.Collection)
	 */
	@Override
	void partition(Collection<Color> pColors) {
		// TODO Auto-generated method stub

	}

}
