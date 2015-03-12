package mw.server.gamelogic.graph;




public abstract class AbstractGraphNode 
{
	private boolean visited = false; 
	public boolean isVisited()
	{
		return visited;
	}
	
	public void setVisited()
	{
		visited = true; 
	}
	public void reset()
	{
		visited = false;
	}
}