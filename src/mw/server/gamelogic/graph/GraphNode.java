package mw.server.gamelogic.graph;

import java.io.Serializable;
import java.util.Collection;

import mw.server.gamelogic.state.Tile;


public class GraphNode extends AbstractGraphNode implements Serializable {

	private final Tile aTile; 
	private Collection<GraphNode> aNeighbors; 
	
	public GraphNode (Tile pTile)
	{
		aTile = pTile; 
	}
	public Tile getTile()
	{
		return aTile; 
	}
	public void initializeNeighbors(Collection<GraphNode> pNeighbors)
	{
		aNeighbors = pNeighbors; 
	}

	
	public Collection<GraphNode> getAdjacentNodes() 
	{
		return aNeighbors; 
	}

}
