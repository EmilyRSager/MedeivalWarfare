package mw.client.controller;

import java.util.HashMap;

import mw.client.gui.ImageTile;
import mw.client.model.ModelTile;

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
	
	private HashMap<ModelTile, ImageTile> tileToTileDisplay;
	
	/* ========================
	 * 		Constructors
	 * ========================
	 */
	
	
	private ModelViewMapping()
	{
		tileToTileDisplay = new HashMap<ModelTile,ImageTile>();
	}
	
	
	/* ========================
	 * 		Public methods
	 * ========================
	 */
	
	
	public ImageTile getTileDisplay(ModelTile t) {
		return tileToTileDisplay.get(t);
	}
	
	public void addBinding(ModelTile t, ImageTile td)
	{
		tileToTileDisplay.put(t,td);
	}
}
