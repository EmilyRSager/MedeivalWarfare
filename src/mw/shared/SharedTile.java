package mw.shared;

public final class SharedTile {

	public enum UnitType { NONE, PEASANT, INFANTRY, SOLDIER, KNIGHT, WATCHTOWER };
	public enum VillageType { NONE, HOVEL, TOWN, FORT };
	public enum Terrain { GRASS, TREE, MEADOW, TOMBSTONE, SEA };
	
	private final SharedColor color;
	private final SharedCoordinates coord;
	private final UnitType unitType;
	private final VillageType villageType;
	private final Terrain terrain;
	private final boolean hasRoad;
	
	public SharedTile(SharedColor color, SharedCoordinates coordinates, UnitType unit, VillageType village, Terrain terrain, boolean hasRoad)
	{
		this.color  =color;
		this.coord = coordinates;
		this.unitType = unit;
		this.villageType  =village;
		this.terrain  =terrain;
		this.hasRoad = hasRoad;
	}
	
	public SharedColor getColor() {
		return color;
	}
	
	public SharedCoordinates getCoordinates() {
		return coord;
	}
	
	public UnitType getUnitType() {
		return unitType;
	}
	
	public VillageType getVillage() {
		return villageType;
	}
	
	public Terrain getTerrain() {
		return terrain;
	}
	
	public boolean hasRoad() {
		return hasRoad;
	}
}
