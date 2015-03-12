package mw.server.gamelogic.graph;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;

/**
 * 
 */
public class Graph <T> implements Serializable {
	private HashMap<T, Collection<T>> aEdges; //adjacency list for neighbors
	
	/**
	 * A tile could extends a graph node which maintains a visited field,
	 * but Tile currently extends Observable and there is no multiple inhertiance in
	 * JAVA. As a work around, maintain a mapping between nodes and visited flags
	 */
	private HashMap<T, Boolean> aVisited;
	
	/**
	 * Constructor
	 * @param pGraph
	 */
	public Graph() {
		aEdges = new HashMap<T, Collection<T>>(); 
		aVisited = new HashMap<T, Boolean>();
	}
	
	/**
	 * @param pNode
	 * @param pNeighbors
	 */
	public void setNeighbors(T pNode, Collection<T> pNeighbors){
		aEdges.put(pNode, pNeighbors);
		aVisited.put(pNode, false);
	}
	
	/**
	 * @param pNode
	 * @return nodes
	 */
	public Collection<T> getNeighbors(T pNode) {
		return aEdges.get(pNode); 
	}
	
	/**
	 * @param pNode
	 * @param pVisited
	 */
	public void setVisited(T pNode, boolean pVisited){
		aVisited.put(pNode, pVisited);
	}
	
	/**
	 * 
	 * @param pNode
	 * @return
	 */
	public boolean isVisited(T pNode){
		return aVisited.get(pNode);
	}
	
	/**
	 * set all nodes in this graph to unvisited
	 */
	public void resetAll()
	{
		for (T lNode: aEdges.keySet())
		{
			setVisited(lNode, false);
		}
	}
	
	/**
	 * @return set of all nodes in this graph
	 */
	public Collection<T> allNodes() {
		return aEdges.keySet(); 
	}
}
