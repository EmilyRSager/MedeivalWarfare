package mw.client.model;

import java.util.HashMap;
import java.util.Map;


/**
 * The GameMap class represents an aggregation of Tiles.
 * @author Hugo KAPP
 *
 */
public final class GameMap {

	private final Map<Coordinates, ModelTile> map;
	
	
	// Constructors
	
	/**
	 * Creates a new empty GameMap (with no tiles)
	 */
	public GameMap(Iterable<ModelTile> tileList)
	{
		map = new HashMap<Coordinates,ModelTile>();
		for (ModelTile t : tileList) {
			map.put(t.getCoordinates(),t);
		}
	}
	
	public GameMap(Map<Coordinates,ModelTile> fullMap) {
		map = fullMap;
	}
	
	
	// Queries
	
	public ModelTile getTile(Coordinates coord) {
		ModelTile ret = map.get(coord);
		if (ret == null)
			throw new IllegalArgumentException("Coordinates "+coord+" don't match any Tile in the map");
		else
			return ret;
	}
	
	/*public void addTile(Tile t)
	{
		if (t==null)
			throw new NullPointerException();
	}*/
}
