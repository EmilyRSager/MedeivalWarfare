package mw.client.controller;

import java.util.HashMap;

public final class ModelViewMapping {

	
	//	Generic Controller
	
	private static ModelViewMapping singleton = null;
	
	public static void initialize() {
		singleton = new ModelViewMapping();
	}
	
	public static void clear() {
		singleton = null;
	}
	
	public static ModelViewMapping singleton() {
		return singleton;
	}
	
	
	//	ModelViewMapping
	
	private HashMap<ImageTile, TileDisplay> tileToTileDisplay;
	
	private ModelViewMapping()
	{
		tileToTileDisplay = new HashMap<ImageTile,TileDisplay>();
	}
	
	public TileDisplay getTileDisplay(ImageTile t) {
		return tileToTileDisplay.get(t);
	}
	
}
