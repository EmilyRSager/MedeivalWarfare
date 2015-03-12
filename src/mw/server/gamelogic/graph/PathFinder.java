package mw.server.gamelogic.graph;


import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

import mw.server.gamelogic.TileGraphLogic;
import mw.server.gamelogic.model.Tile;

public class PathFinder //has classic DFS
{
	/**
	 * DFS for Tiles that the Unit on pSourceTile can move to
	 * @param v
	 * @param g
	 * @return set of tiles that a unit associated with Tile v can move to
	 */
	public static Set<Tile> getReachableTiles(Graph<Tile> pGraph, Tile pSourceTile) 
	{
		Stack<Tile> S = new Stack<Tile>();  
		Set<Tile> reachableTiles = new HashSet<Tile>();
		S.push(pSourceTile);
		
		while (!S.isEmpty())
		{
			Tile crt =  S.pop(); 
			pGraph.setVisited(pSourceTile, true);
			for(Tile lTile : pGraph.getNeighbors(crt)) {
				if(TileGraphLogic.isReachableNode(pSourceTile, lTile)) {
					reachableTiles.add(lTile);
				}
				
				if (!TileGraphLogic.isPathOver(pSourceTile, lTile)) {
					
					//TODO should check this condition first?
					if(!pGraph.isVisited(lTile)) {
						reachableTiles.add(lTile);
						S.push(lTile); 
					}
				}
			}	
		}
		pGraph.resetAll(); 
		return reachableTiles; 
	}
   
	/**
	 * DFS for the Village that pTile is part of. This is used to set up a map at the beginning
	 * of a Game. After a map is partitioned in different colors, this method is called to initialize
	 * villages 
	 * @param pGraph
	 * @param pTile
	 * @return recursive set of neighbors of pTile that have the same color as pTile
	 */
    public static Set<Tile> getVillage(Graph<Tile> pGraph, Tile pSourceTile)
    {
    	Stack<Tile> S = new Stack<Tile>();  
		Set<Tile> villageSet = new HashSet<Tile>(); 
		villageSet.add(pSourceTile);
		
		S.push(pSourceTile);
		while (!S.isEmpty()) {
			Tile crt =  S.pop(); 
			pGraph.setVisited(crt, true);
			
			for(Tile lTile : pGraph.getNeighbors(crt)) {
				if (!TileGraphLogic.tilesAreSameColor(pSourceTile, crt)) {
					continue; 
				}
				
				if (TileGraphLogic.isVillageBoundary(pSourceTile, lTile) 
						&& TileGraphLogic.tilesAreSameColor(pSourceTile, lTile)) {
					
					villageSet.add(lTile);	
				}
				
				if (!TileGraphLogic.isVillageBoundary(pSourceTile, lTile) 
						&& TileGraphLogic.tilesAreSameColor(pSourceTile, lTile)){
					
					//TODO Should check this condition first?
					if (!pGraph.isVisited(lTile)){
						villageSet.add(lTile);
						S.push(lTile);
					}
				}
					
			}	
		}
		pGraph.resetAll();
		return villageSet;
	}
}
