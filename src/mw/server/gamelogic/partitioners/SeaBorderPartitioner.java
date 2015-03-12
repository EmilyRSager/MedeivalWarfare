/**
 * @author Charlie Bloomfield
 * Mar 12, 2015
 */

package mw.server.gamelogic.partitioners;

import java.util.Iterator;

import mw.server.gamelogic.model.GameMap;
import mw.server.gamelogic.model.Tile;

/**
 * 
 */
public class SeaBorderPartitioner implements IMapPartitioner {
	GameMap aGameMap;
	
	/**
	 * 
	 */
	public SeaBorderPartitioner(GameMap pGameMap) {
		//TODO
	}

	/**
	 * @see mw.server.gamelogic.partitioners.IMapPartitioner#partition(mw.server.gamelogic.model.GameMap)
	 */
	@Override
	public void partition() {
		
	}

	/**
	 * 
	 * @param pLayer
	 * @return
	 */
	private Iterator<Tile> layerIterator(int pLayer){
		
	}
}
