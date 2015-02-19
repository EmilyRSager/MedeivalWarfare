package mw.client.model;


/**
 * The GameMap class represents an aggregation of Tiles.
 * @author Hugo KAPP
 *
 */
public final class GameMap {

	
	/**
	 * Creates a new empty GameMap (with no tiles)
	 */
	public GameMap()
	{
		// nothing
	}
	
	public void addTile(Tile t)
	{
		if (t==null)
			throw new NullPointerException();
	}
}
