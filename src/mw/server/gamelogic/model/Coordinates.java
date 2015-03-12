package mw.server.gamelogic.model;

import java.io.Serializable;

public class Coordinates implements Serializable
{
	
	private int aX; 
	private int aY; 
	public Coordinates(int pX, int pY)
	{
		aX = pX;
		aY = pY;
	}
	
	public int getX()
	{
		return aX; 
	}
	public int getY() 
	{
		return aY; 
	}
	
	@Override
	public boolean equals(Object arg)
	{
		if (arg == null || arg.getClass()!=this.getClass())
		{
			return false; 
		}
		
		if (((Coordinates) arg).getX()!=this.getX() || ((Coordinates) arg).getY()!=this.getY() )
		{
		
			return false; 	
		}
		return true; 
	}
	
	@Override 
	public int hashCode() 
	{
		return ((aX * 97) + (aY)); 
	}
}
