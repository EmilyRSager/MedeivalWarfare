/**
 * @author Charlie Bloomfield
 * Mar 11, 2015
 */

package mw.server.gamelogic.partitioners;

import java.util.Collection;
import java.util.Set;

import mw.client.model.GameMap;
import mw.server.gamelogic.Color;
import mw.server.gamelogic.GraphNode;
import mw.server.gamelogic.PathFinder;
import mw.server.gamelogic.RandomColorGenerator;
import mw.server.gamelogic.Tile;
import mw.server.gamelogic.Village;
import mw.server.gamelogic.VillageType;

/**
 * 
 */
public final class RandomMapPartitioner extends AbstractMapPartitioner {

	/**
	 * @param pGameMap
	 */
	public RandomMapPartitioner(GameMap pGameMap) {
		super(pGameMap);
		//TODO
	}

	/**
	 * @see mw.server.gamelogic.partitioners.AbstractMapPartitioner#partition()
	 */
	@Override
	public void partition(Collection<Color> pColors) {
		aGameMap.availableColors.add(Color.NEUTRAL);

		//assign colors to the tiles
		for (GraphNode lGraphNode : graph.allNodes()) {
			Tile lTile = lGraphNode.getTile(); 
			lTile.setColor(RandomColorGenerator.generateRandomColor(availableColors));
		}

		//initializes villages
		for (GraphNode lGraphNode : graph.allNodes())
		{
			Set<GraphNode> villageSet = PathFinder.getVillage(lGraphNode, graph); 
			boolean villageAlreadyExists = false; 

			//makes sure a village doesn't already exist to avoid duplicate references 
			for (Village lVillage: aVillages)
			{
				if (lVillage.getVillageNodes().equals(villageSet) )
				{
					villageAlreadyExists = true;  //needs a better name -- represents whether we should create a village or not
				}
			}

			if (!villageAlreadyExists)
			{
				//don't create a village if it's neutral land, or the village is too small to be supported
				if (villageSet.size()>=3 && villageSet.iterator().next().getTile().getColor()!=Color.NEUTRAL)
				{
					Village v = new Village (villageSet);
					aVillages.add(v); 
					villageAlreadyExists = false; 
				}
				//Non-villages need to be returned to neutral color 
				if (villageSet.size()<3 )
				{
					for (GraphNode vGraphNode: villageSet)
					{
						vGraphNode.getTile().setColor(Color.NEUTRAL);
					}
				}
			}
		}


		for (Village lVillage: aVillages) {
			//only runs 1X per village
			for (Tile lTile: lVillage.getTiles()) {
				lVillage.setCapital(lTile);
				lVillage.setVillageType(VillageType.HOVEL);

				lVillage.addOrSubtractGold(100);
				lVillage.addOrSubtractWood(100);

				break; 

			}
		}
	}
}
