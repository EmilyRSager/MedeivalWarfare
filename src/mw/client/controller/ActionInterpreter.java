package mw.client.controller;

import mw.client.model.*;

public final class ActionInterpreter implements Controller {

	private static ActionInterpreter interpreter;
	
	public static void initialize()
	{
		interpreter = new ActionInterpreter();
	}
	
	public static void clear()
	{
		interpreter = null;
	}
	
	public static ActionInterpreter singleton()
	{
		return interpreter;
	}
	
	private Tile selectedTile = null;
	
	private ActionInterpreter()
	{
		// nothing yet
	}
	
	public void select(/* some form of tile representation, either coordinates or the reference itself */)
	{
		// get the Tile
		Tile target;
		selectedTile = target;
	}
	
}
