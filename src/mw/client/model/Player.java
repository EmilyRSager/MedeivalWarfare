package mw.client.model;

import java.awt.Color;

public final class Player{
	
	private final Color color;
	private final String name;
	
	public Player(Color c, String name)
	{
		color=c;
		this.name = name;
	}
	
	
	//	GETTERS
	
	
	public Color getColor()
	{
		return color;
	}
	
	public String getName() {
		return name;
	}
}
