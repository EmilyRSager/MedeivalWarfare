package mw.client.controller;

import java.util.HashMap;

import mw.client.gui.ImageTile;
import mw.client.model.Tile;

public final class ModelViewMapping {

	
	/* ===============================
	 * 		Generic Controller
	 * ===============================
	 */
	
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
	
	
	/* ===============================
	 * 		ActionInterpreter 
	 * ===============================
	 */
	
	private HashMap<Tile, ImageTile> tileToTileDisplay;
	
	/* ========================
	 * 		Constructors
	 * ========================
	 */
	
	
	private ModelViewMapping()
	{
		tileToTileDisplay = new HashMap<Tile,ImageTile>();
	}
	
	
	/* ========================
	 * 		Public methods
	 * ========================
	 */
	
	
	public ImageTile getTileDisplay(Tile t) {
		return tileToTileDisplay.get(t);
	}
	
	public void addBinding(Tile t, ImageTile td)
	{
		tileToTileDisplay.put(t,td);
	}
}
