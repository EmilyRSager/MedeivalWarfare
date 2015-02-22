package mw.server.gamelogic;




public abstract class AbstractGraphNode 
{
	private boolean visited = false; 
	public void setVisited()
	{
		visited = true; 
	}
	public void reset()
	{
		visited = false;
	}
}
