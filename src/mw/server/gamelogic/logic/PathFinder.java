package mw.server.gamelogic.logic;


import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

import mw.server.gamelogic.graph.Graph;
import mw.server.gamelogic.graph.GraphNode;

public class PathFinder //has classic DFS
{
	/**
	 * @param v
	 * @param g
	 * @return set of tiles that a unit associated with GraphNode v can move to
	 */
	public static Set<GraphNode> getMovableTiles (GraphNode v, Graph g) 
	{
		Stack<GraphNode> S = new Stack <GraphNode>();  
		Set<GraphNode> canMoveHere = new HashSet<GraphNode>();
		S.push(v); 
		while (!S.isEmpty())
		{
			GraphNode crt =  S.pop(); 
			crt.setVisited(); 
			for(GraphNode lGraphNode : g.getAdjacentNodes(crt))
			{
				if(TileGraphLogic.isReachableNode(v, lGraphNode)) 
				{
					canMoveHere.add(lGraphNode);
				}
				if (!TileGraphLogic.isPathOver(v, lGraphNode))
				{
					if(!lGraphNode.isVisited())
					{
						canMoveHere.add(lGraphNode);
						S.push(lGraphNode); 
					}
				}
			}	
		}
		g.resetAll(); 
		return canMoveHere; 
	}
   
    public static Set<GraphNode> getVillage(GraphNode v, Graph g)
    {
    	Stack<GraphNode> S = new Stack <GraphNode>();  
		Set<GraphNode> villageSet = new HashSet<GraphNode>(); 
		S.push(v);
		villageSet.add(v);
		while (!S.isEmpty())
		{
			GraphNode crt =  S.pop(); 
			crt.setVisited(); 
			
			
			for(GraphNode lGraphNode : g.getAdjacentNodes(crt))
			{
				if (!TileGraphLogic.tilesAreSameColor(v, lGraphNode))
				{
					continue; 
				}
				
				if (TileGraphLogic.isVillageBoundary(v, lGraphNode) && TileGraphLogic.tilesAreSameColor(v, lGraphNode)) 
				{
					
					villageSet.add(lGraphNode);
					
				}
				if (!TileGraphLogic.isVillageBoundary(v, lGraphNode) && TileGraphLogic.tilesAreSameColor(v, lGraphNode))
				{
					if (!lGraphNode.isVisited())
					{
						villageSet.add(lGraphNode);
						S.push(lGraphNode);
					}
				}
					
			}	
		}
		g.resetAll();
		return villageSet;
	}
}