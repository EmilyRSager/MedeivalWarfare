package mw.client.controller;

import mw.client.model.Coordinates;
import mw.client.model.Tile;
import mw.client.model.Tile.StructureType;
import mw.shared.SharedCoordinates;
import mw.shared.SharedTile;

public final class NetworkModelTranslator {

	public static Coordinates translateSharedCoordinates(SharedCoordinates sharedCoord)
	{
		return new Coordinates(sharedCoord.getX(),sharedCoord.getY());
	}
	
	public static Tile.Terrain translateSharedTerrain(SharedTile.Terrain sharedTerrain)
	{
		switch (sharedTerrain)
		{
		case GRASS:
			return Tile.Terrain.GRASS;
			
		case TREE:
			return Tile.Terrain.TREE;
			
		case MEADOW:
			return Tile.Terrain.MEADOW;
			
		case TOMBSTONE:
			return Tile.Terrain.TOMBSTONE;
			
		case SEA:
			return Tile.Terrain.SEA;
			
		default:
			throw new IllegalArgumentException("Undefined SharedTile.Terrain value "+sharedTerrain);
		}
	}
	
	public static Tile.UnitType translateSharedUnitType(SharedTile.UnitType sharedUT)
	{
		switch (sharedUT)
		{
		case NONE:
			return Tile.UnitType.NONE;
			
		case PEASANT:
			return Tile.UnitType.PEASANT;
			
		case INFANTRY:
			return Tile.UnitType.INFANTRY;
			
		case SOLDIER:
			return Tile.UnitType.SOLDIER;
			
		case KNIGHT:
			return Tile.UnitType.KNIGHT;
			
		case WATCHTOWER:
			return Tile.UnitType.WATCHTOWER;
			
		default:
			throw new IllegalArgumentException("Undefined SharedTile.UnitType value "+sharedUT);
		}
	}
	
	public static Tile.StructureType translateSharedVillageType(SharedTile.VillageType sharedVT)
	{
		switch (sharedVT)
		{
		case NONE:
			return StructureType.NONE;
			
		case HOVEL:
			return StructureType.HOVEL;
			
		case TOWN:
			return StructureType.TOWN;
			
		case FORT:
			return StructureType.FORT;
			
			default:
				throw new IllegalArgumentException("Undefined SharedTile.VillageType value "+sharedVT);
		}
	}
	
}
