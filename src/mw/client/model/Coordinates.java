package mw.client.model;

public final class Coordinates {
	
	private final int x, y;
	
	public Coordinates(int x, int y)
	{
		this.x=x;
		this.y=y;
	}
	
	@Override
	public int hashCode()
	{
		return x*300+y;
	}
}
