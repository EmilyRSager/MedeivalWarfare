package mw.server.gamelogic.graph;

import java.io.Serializable;
import java.util.Collection;

public class GraphNode implements Serializable {
	private boolean aVisited;
	private Collection<GraphNode> aNeighbors;
	
	/**
	 * @param pGraphNode
	 */
	public void setNeighbor(GraphNode pGraphNode){
		aNeighbors.add(pGraphNode);
	}
	
	/**
	 * @param pNeighbors
	 */
	public void setNeighbors(Collection<GraphNode> pNeighbors)
	{
		aNeighbors = pNeighbors; 
	}

	/**
	 * 
	 * @return
	 */
	public Collection<GraphNode> getNeighbors() 
	{
		return aNeighbors; 
	}
	
	/**
	 * 
	 */
	public void setVisited()
	{
		aVisited = true;
	}
	
	/**
	 * 
	 */
	public void reset(){
		aVisited = false;
	}
}
