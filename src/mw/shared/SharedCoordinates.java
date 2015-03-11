package mw.shared;

import javax.swing.text.html.HTMLDocument.HTMLReader.IsindexAction;

public final class SharedCoordinates {

	private final int x;
	private final int y;
	
	public SharedCoordinates(int x, int y)
	{
		this.x = x;
		this.y = y;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	@Override
	public boolean equals(Object o)
	{
		if (!(o instanceof SharedCoordinates))
			return false;
		
		SharedCoordinates other = (SharedCoordinates)o;
		return x == other.x && y == other.y;
	}
	
}
