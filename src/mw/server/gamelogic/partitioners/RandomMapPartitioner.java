/**
 * @author Charlie Bloomfield
 * Mar 11, 2015
 */

package mw.server.gamelogic.partitioners;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import mw.server.gamelogic.enums.Color;
import mw.server.gamelogic.enums.VillageType;
import mw.server.gamelogic.graph.PathFinder;
import mw.server.gamelogic.state.GameMap;
import mw.server.gamelogic.state.Tile;
import mw.server.gamelogic.state.Village;
import mw.server.gamelogic.util.RandomColorGenerator;
import mw.util.MultiArrayIterable;

/**
 * 
 */
public class RandomMapPartitioner extends AbstractMapPartitioner {
	
	/**
	 * @param pGameMap
	 */
	public RandomMapPartitioner(GameMap pGameMap) {
		super(pGameMap);
	}

	/**
	 * @see mw.server.gamelogic.partitioners.AbstractMapPartitioner#partition()
	 */
	@Override
	public void partition(Collection<Color> pColors) {

		Collection<Village> aVillages = new HashSet<Village>();
 		//assign colors to the tiles
		for (Tile lTile : MultiArrayIterable.toIterable(aGameMap.getTiles())) {
			lTile.setColor(RandomColorGenerator.generateRandomColor(pColors));
		}

		//initializes villages
		for (Tile lTile : MultiArrayIterable.toIterable(aGameMap.getTiles()))
		{
			Set<Tile> villageSet = PathFinder.getVillage( aGameMap.getGraph(), lTile); 
			boolean villageAlreadyExists = false; 

			//makes sure a village doesn't already exist to avoid duplicate references 
			for (Village lVillage: aVillages)
			{
				if (lVillage.getTiles().equals(villageSet) )
				{
					villageAlreadyExists = true;  //needs a better name -- represents whether we should create a village or not
				}
			}

			if (!villageAlreadyExists)
			{
				//don't create a village if it's neutral land, or the village is too small to be supported
				if (villageSet.size()>=3 && villageSet.iterator().next().getColor()!=Color.NEUTRAL)
				{
					Village v = new Village (villageSet);
					aVillages.add(v); 
					villageAlreadyExists = false; 
				}
				//Non-villages need to be returned to neutral color 
				if (villageSet.size()<3 )
				{
					for (Tile vTile: villageSet)
					{
						vTile.setColor(Color.NEUTRAL);
					}
				}
			}
		}


		for (Village lVillage: aVillages) {
			//only runs 1X per village
			for (Tile lTile: lVillage.getTiles()) {
				lVillage.setCapital(lTile);
				lVillage.setVillageType(VillageType.HOVEL);
				break; 

			}
		}
	}
}
