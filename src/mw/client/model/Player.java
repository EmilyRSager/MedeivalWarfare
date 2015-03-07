package mw.client.model;

import mw.shared.SharedColor;


public final class Player{
	
	private final SharedColor color;
	private final String name;
	
	public Player(SharedColor c, String name)
	{
		color=c;
		this.name = name;
	}
	
	
	//	GETTERS
	
	
	public SharedColor getColor()
	{
		return color;
	}
	
	public String getName() {
		return name;
	}
}
