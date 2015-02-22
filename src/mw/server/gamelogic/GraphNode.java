package mw.server.gamelogic;

import java.util.Collection;

public class GraphNode extends AbstractGraphNode {

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
