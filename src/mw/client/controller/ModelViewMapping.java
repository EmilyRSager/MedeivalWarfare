package mw.client.controller;

import java.util.HashMap;

import mw.client.gui.window.ImageTile;
import mw.client.model.ModelTile;

/**
 * The ModelViewMapping controller provides a mapping for all model and view information that
 * are linked together.
 * @author Hugo Kapp
 *
 */
public final class ModelViewMapping {

	
	/* ===============================
	 * 		Generic Controller
	 * ===============================
	 */
	
	private static ModelViewMapping singleton = null;
	
	/**
	 * Initializes the singleton of the ModelViewMapping controller
	 */
	public static void initialize() {
		singleton = new ModelViewMapping();
	}
	
	/**
	 * Clears the ModelViewMapping from its singleton
	 */
	public static void clear() {
		singleton = null;
	}
	
	/**
	 * Gives access to the singleton of the ModelViewMapiing controller
	 * @return
	 */
	public static ModelViewMapping singleton() {
		return singleton;
	}
	
	
	/* ===============================
	 * 		ActionInterpreter 
	 * ===============================
	 */
	
	private final HashMap<ModelTile, ImageTile> modelToDisplayTiles;
	private final HashMap<ImageTile, ModelTile> displayToModelTiles;
	
	
	/* ========================
	 * 		Constructors
	 * ========================
	 */
	
	/**
	 * Creates a new empty ModelViewMapping
	 */
	private ModelViewMapping()
	{
		modelToDisplayTiles = new HashMap<ModelTile,ImageTile>();
		displayToModelTiles = new HashMap<ImageTile,ModelTile>();
	}
	
	
	/* ========================
	 * 		Public methods
	 * ========================
	 */
	
	/**
	 * Gets the ImageTile associated with the given tile
	 * @param t the tile to get the ImageTile associated with
	 * @return the ImageTile associated with the ModelTile t
	 */
	public ImageTile getTileDisplay(ModelTile t) {
		return modelToDisplayTiles.get(t);
	}
	
	/**
	 * Gets the ModelTile associated with the given ImageTile
	 * @param dispTile the ImageTile to get the ModelTile associated with
	 * @return the ModelTile associated with the given ImageTile
	 */
	public ModelTile getModelTile(ImageTile dispTile)
	{
		return displayToModelTiles.get(dispTile);
	}
	
	/**
	 * Adds a new binding between a ModelTile and an ImageTile
	 * @param t the tile to bind with an ImageTile
	 * @param td the ImageTile to bind the ModelTile with
	 */
	public void addBinding(ModelTile t, ImageTile td)
	{
		modelToDisplayTiles.put(t,td);
		displayToModelTiles.put(td, t);
	}
}
