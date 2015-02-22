package mw.server.gamelogic;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

public class Graph {

	
	private HashMap< GraphNode, Collection<GraphNode>> aGraph; 
	public Graph(HashMap<GraphNode, Collection<GraphNode>> pGraph) {
		aGraph = pGraph; 
		
	}
	public Collection<GraphNode> getAdjacentNodes(GraphNode pNode) {
		return aGraph.get(pNode); 
	}
	public void resetAll()
	{
		for (GraphNode lGraphNode: aGraph.keySet())
		{
			lGraphNode.reset();
		}
	}
	public Collection<GraphNode> allNodes()
	{
		return aGraph.keySet(); 
	}
}
