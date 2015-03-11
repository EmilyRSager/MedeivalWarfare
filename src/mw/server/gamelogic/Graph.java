package mw.server.gamelogic;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;


public class Graph implements Serializable {

	
	private HashMap< GraphNode, Collection<GraphNode>> aGraph; //adjacency list for neighbours
	public Graph(HashMap<GraphNode, Collection<GraphNode>> pGraph) {
		aGraph = pGraph; 
		
	}
	public Collection<GraphNode> getAdjacentNodes(GraphNode pNode) {
		return aGraph.get(pNode); 
	}
	public void resetAll() //resetting the visited field for all the nodes to false
	{
		for (GraphNode lGraphNode: aGraph.keySet())
		{
			lGraphNode.reset();
		}
	}
	public Collection<GraphNode> allNodes() //returns all the nodes in the graph
	{
		return aGraph.keySet(); 
	}
}
