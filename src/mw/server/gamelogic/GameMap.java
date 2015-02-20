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
public class GameMap extends RandomColorGenerator {


	//TODO 
	//1. Randomly initialize trees 

	private static HashMap<Tile, ArrayList<Tile>> TileAdjList;
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
		for (Tile lTile : TileAdjList.keySet()) 
		{
			int i = rTreesAndMeadows.nextInt(9);  
			lTile.setColor(generateRandomColor());
			if(i == 4 || i == 7) // 2/10 times this will happen
			{
				lTile.setStructureType(StructureType.TREE);
			}
			else if (i == 2) // 1/10 times meadows are put on tiles  
			{	
			lTile.setHasMeadow(true); 
			}
		}
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
     * @param lTile
     * @return
     */
    public ArrayList<Tile> getSameColorTiles(Tile pTile) 
    {
		//basic BFS that stops whenever we reach a tile with a diff color than our own
    	Queue<Tile> exploredTiles = new LinkedList<Tile>();
    	Set<Tile> rTiles = new HashSet<Tile>(); 
    	ArrayList<Tile> toReturn = new ArrayList<Tile>();
    	rTiles.add(pTile);
    	exploredTiles.add(pTile);
    	while (!exploredTiles.isEmpty())
    	{
    		Tile crtTile = exploredTiles.remove(); 
    		if (crtTile.getColor() == pTile.getColor())
    		{
    			toReturn.add(pTile);
    			for(Tile lTile : TileAdjList.get(crtTile))
    			{
    				if(!rTiles.contains(lTile))
    				{
    					rTiles.add(lTile);
    				exploredTiles.add(lTile);
    				}
    			}
    		}
    		
    	}
    	return toReturn; 
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
    		TileAdjList = new HashMap<Tile, ArrayList<Tile>>(); 
    		aTiles = new Tile[height][width]; 
    	
    		for (int i = 0; i< height; i++ )
    		{
    			for (int j =0; j <width; j++)
    			{
    				aTiles[i][j] = new Tile (StructureType.NO_STRUCT, j, i); 
    			}
    		}
    		for (int i = 0; i<height; i++)
    		{
    			for (int j =0; j<width; j++)
    			{
    				//for the left upper corner
    				if (i==0 && j == 0)
    				{
    					ArrayList<Tile> tmp = new ArrayList<Tile>(); 
    					tmp.add(aTiles[i][j+1]); //lower left 
    					tmp.add(aTiles[i+1][j]); //directly below
    					TileAdjList.put(aTiles[i][j], (ArrayList<Tile>)tmp.clone());
    					aTiles[i][j].initializeNeighbors(tmp);//you can safely remove the cloning                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                              
    				}
    				
    				//for the left column 
    				if (i!=0 && i!=height-1&& j==0)
    				{
    					ArrayList<Tile> tmp = new ArrayList<Tile>(); 
    					tmp.add(aTiles[i-1][j]); // directly above
    					tmp.add(aTiles[i+1][j]);// directly below
    					tmp.add(aTiles[i-1][j+1]); //upper right
    					tmp.add(aTiles[i][j+1]); //lower right
    					TileAdjList.put(aTiles[i][j], (ArrayList<Tile>)tmp.clone());
    					aTiles[i][j].initializeNeighbors(tmp);
    				}
    				
    				/*for the upper right corner
    				 * Need even and odd cases 
    				 */
    				if (i==0 && j == width -1)
    				{
    					if (j%2 == 0) //even case
    					{
    						ArrayList<Tile> tmp = new ArrayList<Tile>(); 
    						tmp.add(aTiles[i][j-1]); //lower left
    						tmp.add(aTiles[i+1][j]); //directly below 
    						TileAdjList.put(aTiles[i][j], (ArrayList<Tile>)tmp.clone());
    						aTiles[i][j].initializeNeighbors(tmp);
    					}
    					else //odd case
    					{
    						ArrayList<Tile> tmp = new ArrayList<Tile>(); 
    						tmp.add(aTiles[i][j-1]); //upper left
    						tmp.add(aTiles[i+1][j-1]); //lower left
    						tmp.add(aTiles[i+1][j]); // directly below
    						TileAdjList.put(aTiles[i][j], (ArrayList<Tile>)tmp.clone());
    						aTiles[i][j].initializeNeighbors(tmp);
    					}
    				}
    				
    				/*for the right column
    				 * Need even and odd cases
    				 */
    				if (i !=0 && i!=height-1 && j == width-1)
    				{
    					if (j%2 == 0) //even case
    					{
    						ArrayList<Tile> tmp = new ArrayList<Tile>(); 
    						tmp.add(aTiles[i][j-1]); //lower left
    						tmp.add(aTiles[i-1][j-1]); //upper left
    						tmp.add(aTiles[i-1][j]); //directly above
    						tmp.add(aTiles[i+1][j]); //directly below 
    						TileAdjList.put(aTiles[i][j], (ArrayList<Tile>)tmp.clone());
    						aTiles[i][j].initializeNeighbors(tmp);
    					}
    					else //odd case
    					{
    						ArrayList<Tile> tmp = new ArrayList<Tile>(); 
    						tmp.add(aTiles[i][j-1]); //upper left
    						tmp.add(aTiles[i+1][j-1]); //lower left
    						tmp.add(aTiles[i+1][j]); // directly below
    						tmp.add(aTiles[i-1][j]); 
    						TileAdjList.put(aTiles[i][j], (ArrayList<Tile>)tmp.clone());
    						aTiles[i][j].initializeNeighbors(tmp);
    					}
    				}
    				
    				
    				/*non-edge hex
    				 * conditions:
    				 *		j is odd 
    				 */
    				if (j!=0 && j!=width-1 && j%2==1) {
    					if (i!=0 && i !=height-1)  //if its not a top or bottom row
    					{ 
    						ArrayList<Tile> tmp = new ArrayList<Tile>(); 
    						tmp.add(aTiles[i-1][j]); //directly above 
    						tmp.add(aTiles[i][j-1]); //upper left
    						tmp.add(aTiles[i][j+1]); //upper right
    						tmp.add(aTiles[i+1][j]); //directly below
    						tmp.add(aTiles[i+1][j-1]); //lower left
    						tmp.add(aTiles[i+1][j+1]); //lower right
    						TileAdjList.put(aTiles[i][j], (ArrayList<Tile>)tmp.clone());
    						aTiles[i][j].initializeNeighbors(tmp);
    					}
    					if (i==0) // if its a top row
    					{
    						ArrayList<Tile> tmp = new ArrayList<Tile>(); 
    						tmp.add(aTiles[i+1][j]); //directly below
    						tmp.add(aTiles[i+1][j-1]); //lower left
    						tmp.add(aTiles[i+1][j+1]); //lower right
    						TileAdjList.put(aTiles[i][j], (ArrayList<Tile>)tmp.clone());
    						aTiles[i][j].initializeNeighbors(tmp);
    					}
    					if (i==height-1) //if its a bottom row
    					{
    						ArrayList<Tile> tmp = new ArrayList<Tile>(); 
    						tmp.add(aTiles[i-1][j]); //directly above 
    						tmp.add(aTiles[i][j-1]); //upper left
    						tmp.add(aTiles[i][j+1]); //upper right
    						TileAdjList.put(aTiles[i][j], (ArrayList<Tile>)tmp.clone());
    						aTiles[i][j].initializeNeighbors(tmp);
    					}
    				}
    				
    				
    				
    				/*non-edge hex
    				 * conditions: 
    				 * 		j is even 
    				 */
    				if (j!=0 && j!=width-1 && j %2 == 0)
    				{
    					if (i!=0 && i!=height-1) //if its not a top or bottom row
    					{
    					ArrayList<Tile> tmp = new ArrayList<Tile>(); 
    					tmp.add(aTiles[i-1][j]); //directly above
    					tmp.add(aTiles[i+1][j]); //directly below
    					tmp.add(aTiles[i-1][j-1]); //upper left
    					tmp.add(aTiles[i-1][j+1]); //upper right
    					tmp.add(aTiles[i][j-1]); //lower left
    					tmp.add(aTiles[i][j+1]); //lower right
    					TileAdjList.put(aTiles[i][j], (ArrayList<Tile>)tmp.clone());
    					aTiles[i][j].initializeNeighbors(tmp);
    					
    					}
    					
    					if (i==0) //if its a top row
    					{
    						ArrayList<Tile> tmp = new ArrayList<Tile>(); 
    						tmp.add(aTiles[i+1][j]); //directly below
    						tmp.add(aTiles[i][j-1]); //lower left
        					tmp.add(aTiles[i][j+1]); //lower right
        					TileAdjList.put(aTiles[i][j], (ArrayList<Tile>)tmp.clone());
        					aTiles[i][j].initializeNeighbors(tmp);
    					}
    					if (i== height -1) // if its a bottom row
    					{
    						ArrayList<Tile> tmp = new ArrayList<Tile>(); 
        					tmp.add(aTiles[i-1][j]); //directly above
        					tmp.add(aTiles[i-1][j-1]); //upper left
        					tmp.add(aTiles[i-1][j+1]); //upper right
        					TileAdjList.put(aTiles[i][j], (ArrayList<Tile>)tmp.clone());
        					aTiles[i][j].initializeNeighbors(tmp);
    					}
    					
    				}
    				
    			}
    		}
    		
    	}
     
        
    

    public Set<Tile> getMovableTiles(Tile start) {
    	Set<Tile> reachableTiles = new HashSet<Tile>(); 
    	if (!TileAdjList.containsKey(start))
    	{
    		throw new TileNotFound ("The specified tile is not in the game map"); 

    	}

    	else //depth first search

    	{
    		Color startVillageColor = start.getColor();  
    		Unit startUnit = start.getUnit(); 
    		UnitType startUnitType = startUnit.getUnitType(); 
    		Set<Tile> exploredTiles = new HashSet<Tile>();
    		
    		Stack<Tile> S = new Stack<Tile>(); 
    		S.push(start);
    		Tile crt; 
    		while (!S.isEmpty()) 
    		{ 
    			crt = S.pop(); 
    			if (!exploredTiles.contains(crt)) 
    			{
    				exploredTiles.add(crt);
    				ArrayList<Tile> adjTiles = TileAdjList.get(crt); 
    				for (Tile lTile: adjTiles)
    				{
    					if (lTile.getColor().equals(Color.SEATILE))
    					{
    						continue; 
    					}
    					Unit lUnit = lTile.getUnit(); 
    					boolean isUnitOnTile = false ; 
    					if (lUnit != null) 
    					{
    						isUnitOnTile = true; 
    					}
    					Color lVillageColor = lTile.getColor(); 
    					StructureType lStructureType = lTile.getStructureType(); 
    					/*
    					 * The case where we are moving a soldier, peasant, or infantry
    					 */

    					if (startUnitType!=UnitType.KNIGHT) {

    						if (lVillageColor==(startVillageColor)) //We are just moving within our own village

    						{
    							if (isUnitOnTile)  // if there's a unit on the tile we want to move to, we can't move there
    							{
    								continue; //don't explore the neighbors of this node
    							}
    							else //there is no unit on the tile 
    							{
    								if(lStructureType == StructureType.TREE || lStructureType == StructureType.TOMBSTONE) 
    								{
    									reachableTiles.add(lTile); 
    									continue; // DONT Push the tile to the stack, because we can't reach the neighbors of the tree tile via lTile
    								}
    								if(lStructureType == StructureType.NO_STRUCT || lStructureType == StructureType.ROAD)
    								{
    									reachableTiles.add(lTile); 
    									S.push(lTile);  //We want to explore the Tiles adjacent to any in village tile we can move to 
    								}

    							}
    						}
    						else
    						{
    							if (startVillageColor==(Color.NEUTRAL))
    							{
    								if(lStructureType == StructureType.TREE || lStructureType == StructureType.TOMBSTONE) 
    								{
    									reachableTiles.add(lTile); 
    									continue; // DONT Push the tile to the stack, because we can't reach the neighbors of the tree tile via lTile
    								}
    								if(lStructureType == StructureType.NO_STRUCT || lStructureType == StructureType.ROAD)
    								{
    									reachableTiles.add(lTile); 
    									continue; //WE are outside a village and thus can only move 1 outside a village, which would be lTile, so no need to check out its neighbors
    								}
    							}
    							if (lVillageColor != startVillageColor && lVillageColor!=Color.NEUTRAL ) //Enemy territory
    							{

    								if(lTile.isProtected(startUnitType)) // if the tile is protected by enemy villagers 
    								{
    									continue; //can't move there
    								}
    								else { // the tile is unprotected 
    									if(lStructureType == StructureType.TREE || lStructureType == StructureType.TOMBSTONE) 
    									{
    										reachableTiles.add(lTile); 
    										continue; // DONT Push the tile to the stack, because we can't reach the neighbors of the tree tile via lTile
    									}
    									if(lStructureType == StructureType.NO_STRUCT || lStructureType == StructureType.ROAD)
    									{
    										reachableTiles.add(lTile); 
    										continue; // outside a village and thus can only move 1 outside a village, which would be lTile, so no need to check out its neighbors
    									}
    								}
    							}
    						}
    					}

    					/*
    					 * The case where we are moving a knight
    					 */
    					else 
    					{

    						if (lVillageColor==(startVillageColor))// just moving within our own village
    						{
    							if (isUnitOnTile)  // if there's a unit on the tile we want to move to, we can't move there
    							{
    								continue; //don't explore the neighbors of this node
    							}

    							else //there is no unit on the tile 
    							{
    								if(lStructureType == StructureType.TREE || lStructureType == StructureType.TOMBSTONE) 
    								{

    									continue; // can't move to tree or Tombstones
    								}
    								if(lStructureType == StructureType.NO_STRUCT || lStructureType == StructureType.ROAD)
    								{
    									reachableTiles.add(lTile); 
    									S.push(lTile);  //We want to explore the Tiles adjacent to any in village tile we can move to 
    								}

    							}

    						}
    						else { //enemy village or neutral   
    							if (!lTile.isProtected(startUnitType))
    							{
    								if (lStructureType == StructureType.TREE || lStructureType == StructureType.TOMBSTONE)
    								{
    									continue; //can't move here, so not added to reachable tiles 
    								}
    								if (lStructureType == StructureType.NO_STRUCT || lStructureType == StructureType.ROAD) 
    								{
    									reachableTiles.add(lTile); 
    								}
    							}
    						}

    					}
    				}
    			}

    		}



    	}
    	return reachableTiles; 
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
}
