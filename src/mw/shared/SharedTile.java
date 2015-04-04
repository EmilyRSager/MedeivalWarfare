package mw.shared;

/**
 * The SharedTile class represents the state of a Tile. It has all the information the Client needs to
 * display the Tile, in the simplest form possible.
 * @author Hugo KAPP
 *
 */
public final class SharedTile {

	public enum UnitType { NONE, PEASANT, INFANTRY, SOLDIER, KNIGHT, WATCHTOWER };
	public enum VillageType { NONE, HOVEL, TOWN, FORT };
	public enum Terrain { GRASS, TREE, MEADOW, TOMBSTONE, SEA };
	
	private final SharedColor color;
	private final Coordinates coord;
	private final Terrain terrain;
	private final boolean hasRoad;
	
	private final UnitType unitType;
	
	private final VillageType villageType;
	private final int villageGold;
	private final int villageWood;
	
	
	// CONSTRUCTORS
	
	/**
	 * Creates a new SharedTile, filled with the given parameters
	 * @param color : the color of the SharedTile
	 * @param coordinates : the coordinates of the SharedTile
	 * @param terrain : the Terrain of the SharedTile
	 * @param hasRoad : represents the fact that the SharedTile has a road or not
	 * @param unit : the unit type of the unit on the SharedTile
	 * @param village : the type of village on the SharedTile
	 * @param gold : the gold of the village on this SharedTile
	 * @param wood : the wood of the village on this SharedTile
	 * @throws IllegalArgumentException if the color or the coordinates are invalid
	 */
	public SharedTile(SharedColor color, Coordinates coordinates, Terrain terrain, boolean hasRoad, UnitType unit, VillageType village, int gold, int wood)
	{
		if (color==null || coordinates==null)
			throw new IllegalArgumentException("A SharedTile needs a color and coordinates");
		this.color = color;
		this.coord = coordinates;
		this.unitType = unit;
		this.villageType = village;
		this.terrain = terrain;
		this.hasRoad = hasRoad;
		villageGold = gold;
		villageWood = wood;
	}
	
	/**
	 * Creates a new SharedTile with no village on it
	 * @param color : the color of the SharedTile
	 * @param coordinates : the coordinates of the SharedTile
	 * @param terrain : the Terrain of the SharedTile
	 * @param hasRoad : represents the fact that the SharedTile has a road or not
	 * @param unit : the unit type of the unit on the SharedTile
	 */
	public SharedTile(SharedColor color, Coordinates coordinates, Terrain terrain, boolean hasRoad, UnitType unit)
	{
		this(color,coordinates,terrain,hasRoad,unit,VillageType.NONE,0,0);
	}
	
	/**
	 * Creates a new SharedTile with no unit nor a Village on it
	 * @param color : the color of the SharedTile
	 * @param coordinates : the coordinates of the SharedTile
	 * @param terrain : the Terrain of the SharedTile
	 * @param hasRoad : represents the fact that the SharedTile has a road or not
	 */
	public SharedTile(SharedColor color, Coordinates coordinates, Terrain terrain, boolean hasRoad)
	{
		this(color,coordinates,terrain,hasRoad,UnitType.NONE,VillageType.NONE,0,0);
	}
	
	
	// GETTERS
	
	public SharedColor getColor() {
		return color;
	}
	
	public Coordinates getCoordinates() {
		return coord;
	}
	
	public Terrain getTerrain() {
		return terrain;
	}
	
	public boolean hasRoad() {
		return hasRoad;
	}
	
	public UnitType getUnitType() {
		return unitType;
	}
	
	public VillageType getVillage() {
		return villageType;
	}
	
	public int getVillageGold() {
		return villageGold;
	}
	
	public int getVillageWood() {
		return villageWood;
	}
}
