package mw.server.gamelogic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

public class PathFinder 
{

    public static Set<Tile> getMovableTiles(Tile start, HashMap<Tile, ArrayList<Tile>> TileAdjList) {
    	Set<Tile> reachableTiles = new HashSet<Tile>(); 
    	if (!TileAdjList.containsKey(start))
    	{
    		throw new TileNotFound ("The specified tile is not in the game map"); 

    	}

    	else //depth first search

    	{
    		Color startVillageColor = start.getColor();  
    		Unit startUnit = start.getUnit(); 
    		UnitType startUnitType = UnitType.NO_UNIT; 
    		if (startUnit!=null) 
    		{
    		 startUnitType = startUnit.getUnitType(); 
    		 }
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
    public static ArrayList<Tile> getSameColorTile(Tile pTile, HashMap<Tile, ArrayList<Tile>> TileAdjList)
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
}
