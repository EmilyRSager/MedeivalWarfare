package mw.shared;

/**
 * 
 */
public final class Coordinates {

	public final int X;
	public final int Y;
	
	public Coordinates(int X, int Y)
	{
		this.X = X;
		this.Y = Y;
	}
	
	@Override public boolean equals(Object o)
	{
		if (!(o instanceof Coordinates))
			return false;
		
		Coordinates other = (Coordinates) o;
		return X == other.X && Y == other.Y;
	}
	
	@Override public int hashCode(){
		return X * 300 + Y;
	}
	
	@Override public String toString(){
		return "(" + X + "," + Y + ")";
	}
}
