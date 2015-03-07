package mw.client.model;

public final class Coordinates {
	
	private final int x, y;
	
	public Coordinates(int x, int y)
	{
		this.x=x;
		this.y=y;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	@Override
	public int hashCode()
	{
		return x*300+y;
	}
	
	@Override
	public boolean equals(Object o)
	{
		if (o instanceof Coordinates)
		{
			Coordinates c = (Coordinates)o;
			return x==c.x && y==c.y;
		}
		return false;
	}
	
	@Override
	public String toString() {
		return "("+x+","+y+")";
	}
}
