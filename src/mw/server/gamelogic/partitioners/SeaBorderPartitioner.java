/**
 * @author Charlie Bloomfield
 * Mar 12, 2015
 */

package mw.server.gamelogic.partitioners;

import java.util.Collection;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import mw.server.gamelogic.enums.Color;
import mw.server.gamelogic.state.GameMap;
import mw.server.gamelogic.state.Tile;

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
	public void partition(Collection<Color> pColors) {
		populateOuterLayersWithSeaTiles(3);
	}
	
	
	public void putSeaTilesOnTheMap()
	{
		Random r = new Random();
		double Probability = .2; 
		for (Tile lTile: aGameMap.getGraph().allNodes())
		{
			if (lTile.getCoordinates().X == 0 || lTile.getCoordinates().Y == 0 || lTile.getCoordinates().X ==17 || lTile.getCoordinates().Y ==17 )
			{
				lTile.setColor(Color.SEATILE);
			}
			if ( (lTile.getCoordinates().X == 1 || lTile.getCoordinates().X == 16))
			{
					lTile.setColor(Color.SEATILE);
			}
			if ( (lTile.getCoordinates().Y == 1 || lTile.getCoordinates().Y == 16))
			{
					lTile.setColor(Color.SEATILE);
			}
			if (lTile.getCoordinates().X == 2 || lTile.getCoordinates().Y == 2 || lTile.getCoordinates().X == 15 || lTile.getCoordinates().Y == 15)
			{
				if (r.nextDouble() < Probability)
				{
					lTile.setColor(Color.SEATILE);
				}
			}
		}
		for (Tile lTile: aGameMap.getGraph().allNodes())
		{
			if (lTile.getColor()!= Color.SEATILE)
			{
				lTile.setColor(Color.NEUTRAL);
			}
		}
	}
	/**
	 * Probabilistically populate the outer layers of GameMap as SeaTiles 
	 * @param pNumLayers
	 */
	private void populateOuterLayersWithSeaTiles(int pNumLayers){
		Random lRandom = new Random();
		double lProbabilty;
		
		for(int lLayer = 0; lLayer < pNumLayers; lLayer++){
			//lProbabilty is 0 on the outermost layer, 1/3 on next outermost, 2/3 ....
			lProbabilty = (double) lLayer / (double) pNumLayers;
			for(Tile lTile : getLayer(lLayer)){
				if(lRandom.nextDouble() > lProbabilty){
					lTile.setColor(Color.SEATILE);
				}
			}
		}
	}
	
	
	/**
	 * Returns a set of tiles that constitute the 'ith' layer of a 2d array of tiles.
	 * getLayer(0) will return the outermost shell of a 2d array of tiles,
	 * getLayer(1) will return the next outermost shell of a 2d array of tiles
	 * and so on. This is used to get the border of a map.
	 * 
	 * @param pLayer
	 * @return Set of Tiles in the ith layer of aGameMaps tiles
	 */
	private Set<Tile> getLayer(int pLayer){
		Set<Tile> lLayerTiles = new HashSet<Tile>();
		Tile[][] lTiles = aGameMap.getTiles();
		
		int lWidth = lTiles[0].length;
		int lHeight = lTiles.length;
		
		//traverse horizontally
		for(int i = pLayer; i < (lWidth - pLayer); i++){
			lLayerTiles.add(lTiles[pLayer][i]); //
			lLayerTiles.add(lTiles[lHeight - pLayer][i]);
		}
		
		for(int j = pLayer + 1; j < (lHeight - pLayer - 1); j++){
			lLayerTiles.add(lTiles[j][pLayer]);
			lLayerTiles.add(lTiles[j][lWidth - pLayer]);
		}
		
		return lLayerTiles;
	}
}
