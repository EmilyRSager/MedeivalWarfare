package mw.server.gamelogic;

import java.awt.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Random;
import java.util.Set;
import java.util.Stack;
import java.util.Queue; 



/**
 * GameMap class definition.
 * 
 */
public class GameMap  {


	//TODO 
	//1. Randomly initialize trees 

	private static HashMap<Hexagon, ArrayList<Hexagon>> graph;
	private ArrayList<Village> aVillages;  
	private Tile[][] aTiles; 
	private static Random rTreesAndMeadows = new Random(); 

	/**
	 * Takes the map and randomly colors the tiles
	 * Initializes villages on same color groups of Tiles >=3
	 * Randomly generates trees with (20%) probability
	 * Randomly generates meadows with (10%) probability
	 */

	public void partition() 
	{
		Village lVillage;
		ArrayList<Tile> aReachableTiles = new ArrayList<Tile>();
		boolean createNewVillage = true;

		for (Tile lTile : TileAdjList.keySet()) {
			if (lTile.getColor()!=Color.SEATILE && lTile.getColor()!=Color.NEUTRAL){
				aReachableTiles = getSameColorTiles(lTile);  //this will get all the tiles that can be reached at this point in the construction of the map 
				if (aReachableTiles.size() >= 3) 
				{
					for (Village v : aVillages)
					{
						if (v.getTiles().equals(aReachableTiles))
						{ 
							createNewVillage = false; 
						}

						if (createNewVillage) 
						{	
							lVillage = new Village(aReachableTiles);  
							aVillages.add(lVillage);
						}

					}

				}
			}

		}
	}


	/**
	 * for generating villages/ seeing which villages aTile is in
	 * @param pTile
	 * @return
	 */
	public ArrayList<Tile> getSameColorTiles(Tile pTile) 
	{
		return PathFinder.getSameColorTile(pTile, TileAdjList); 
	}



	/**
	 * 
	 * @param height
	 * @param width
	 * @param rGenerate
	 * generates a new map -- creates tiles of the given map size, stores tiles 
	 * in a double array (which the gui can observe). 
	 * Tiles are also stored in an adjacency list which the server code can use to find paths
	 * 
	 */
	public GameMap (int height, int width) {
		aVillages = new ArrayList<Village>(); 

		aTiles = new Tile[height][width]; 

		for (int i = 0; i< height; i++ )
		{
			for (int j =0; j <width; j++)
			{
				aTiles[i][j] = new Tile (StructureType.NO_STRUCT, i, j); 

			}

		}


		HexToGraph converter = new HexToGraph(); 
		graph = converter.ConvertFlatToppedHexes(aTiles);
		for (Hexagon lHex : graph.keySet()) 

		{
			int i = lHex.getCoordinates()[0];
			int j = lHex.getCoordinates()[1];
			int k = rTreesAndMeadows.nextInt(9);  
			aTiles[i][j].setColor(RandomColorGenerator.generateRandomColor());
			if(k == 4 || k == 7) // 2/10 times this will happen
			{
				aTiles[i][j].setStructureType(StructureType.TREE);
			}
			else if (k == 2) // 1/10 times meadows are put on tiles  
			{	
				aTiles[i][j].setHasMeadow(true); 
			}
		}
	}
    		
    			
    				
    				
    				
    		
    				
    				
    				
    		/*
    		 *Randomly generates structures etc.  
    		 */
    		
     
        
    
public Set<Tile> getMovableTiles(Tile start)
{
	return PathFinder.getMovableTiles(start, TileAdjList); 
}

    /**
     * 
     * @param v1
     * @param v2
     * @return
     * Can Write after the demo
     */
 	public boolean canFuse(Village v1, Village v2)
    {
    	return false; 
    }

    /**
     * 
     * 
     * @param invadedVillage
     * @param invadingVillage
     * can write after the demo
     */
	public void fuseVillages(Village invadedVillage, Village invadingVillage) {
		// TODO Auto-generated method stub
		
	}

	public ArrayList<Village> getNeighboringVillages(Tile pTile) {
		// TODO Auto-generated method stub
		return null; 
		
	}

	/**
	 * 
	 * @param crtTile
	 * @param pDestinationTile
	 * @return
	 * 
	 */
	public boolean getPath(Tile crtTile, Tile pDestinationTile) {
		if( getMovableTiles(crtTile).contains(pDestinationTile))
		{
			return true; 
		}
		return false;
	}

	/**
	 * Given coordinates, fetches the tile
	 */
	public Hexagon getHexagon(int X, int Y)
	{
		
		for (Hexagon lHexagon : graph.keySet())
		{
			int [] c = lHexagon.getCoordinates(); 
			if (c[0] == X) 
			{
				if (c[1] == Y)
				{
				 return lHexagon; 
				}
			}
	}
		return null; 
	}
	}
