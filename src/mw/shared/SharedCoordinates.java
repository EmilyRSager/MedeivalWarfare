package mw.shared;


public final class SharedCoordinates {

	public final int X;
	public final int Y;
	
	public SharedCoordinates(int X, int Y)
	{
		this.X = X;
		this.Y = Y;
	}
	
	@Override
	public boolean equals(Object o)
	{
		if (!(o instanceof SharedCoordinates))
			return false;
		
		SharedCoordinates other = (SharedCoordinates) o;
		return X == other.X && Y == other.Y;
	}
	
}
