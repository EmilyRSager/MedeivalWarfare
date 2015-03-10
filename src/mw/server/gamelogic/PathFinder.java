package mw.server.gamelogic;


import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

public class PathFinder //has classic DFS
{
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
					canMoveHere.add(lGraphNode);
					S.push(lGraphNode); 
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
