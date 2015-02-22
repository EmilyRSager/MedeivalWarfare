package mw.client.model;

import java.util.HashMap;
import java.util.Map;


/**
 * The GameMap class represents an aggregation of Tiles.
 * @author Hugo KAPP
 *
 */
public final class GameMap {

	private final Map<Coordinates, Tile> map;
	
	
	// Constructors
	
	/**
	 * Creates a new empty GameMap (with no tiles)
	 */
	public GameMap(Iterable<Tile> tileList)
	{
		map = new HashMap<Coordinates,Tile>();
		for (Tile t : tileList) {
			map.put(t.getCoordinates(),t);
		}
	}
	
	public GameMap(Map<Coordinates,Tile> fullMap) {
		map = fullMap;
	}
	
	
	// Queries
	
	public Tile getTile(Coordinates coord) {
		return map.get(coord);
	}
	
	/*public void addTile(Tile t)
	{
		if (t==null)
			throw new NullPointerException();
	}*/
}
