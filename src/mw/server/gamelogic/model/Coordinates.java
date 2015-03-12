package mw.server.gamelogic.model;

import java.io.Serializable;

/**
 * Immutable representation of a 2d Cartesian coordinates
 */
public final class Coordinates implements Serializable {
	public final int X; 
	public final int Y;
	
	/**
	 * Constructor
	 * @param pX
	 * @param pY
	 */
	public Coordinates(int pX, int pY){
		X = pX;
		Y = pY;
	}
	
	@Override
	public boolean equals(Object arg) {
		//null and class check
		if (arg == null || arg.getClass() != this.getClass()) {
			return false; 
		}
		
		Coordinates target = (Coordinates) arg;
		
		return target.X == X && target.Y == Y;
	}
	
	@Override 
	public int hashCode() 
	{
		return (X * 97) + Y; 
	}
}
