package mw.client.controller;

import mw.client.model.Coordinates;
import mw.client.model.ModelTile;
import mw.client.model.ModelTile.StructureType;
import mw.shared.SharedCoordinates;
import mw.shared.SharedTile;

public final class NetworkModelTranslator {

	public static ModelTile translateSharedTile(SharedTile sharedTile)
	{
		if (sharedTile==null)
			return null;
		ModelTile newTile = new ModelTile(sharedTile.getCoordinates().getX(), sharedTile.getCoordinates().getY());
		newTile.setColor(sharedTile.getColor());
		newTile.setRoad(sharedTile.hasRoad());
		newTile.setStructureType(translateSharedVillageType(sharedTile.getVillage()));
		newTile.setTerrain(translateSharedTerrain(sharedTile.getTerrain()));
		newTile.setUnitType(translateSharedUnitType(sharedTile.getUnitType()));
		newTile.setVillageGold(sharedTile.getVillageGold());
		newTile.setVillageWood(sharedTile.getVillageWood());
		return newTile;
	}
	
	public static Coordinates translateSharedCoordinates(SharedCoordinates sharedCoord)
	{
		return new Coordinates(sharedCoord.getX(),sharedCoord.getY());
	}
	
	public static ModelTile.Terrain translateSharedTerrain(SharedTile.Terrain sharedTerrain)
	{
		switch (sharedTerrain)
		{
		case GRASS:
			return ModelTile.Terrain.GRASS;
			
		case TREE:
			return ModelTile.Terrain.TREE;
			
		case MEADOW:
			return ModelTile.Terrain.MEADOW;
			
		case TOMBSTONE:
			return ModelTile.Terrain.TOMBSTONE;
			
		case SEA:
			return ModelTile.Terrain.SEA;
			
		default:
			throw new IllegalArgumentException("Undefined SharedTile.Terrain value "+sharedTerrain);
		}
	}
	
	public static ModelTile.UnitType translateSharedUnitType(SharedTile.UnitType sharedUT)
	{
		switch (sharedUT)
		{
		case NONE:
			return ModelTile.UnitType.NONE;
			
		case PEASANT:
			return ModelTile.UnitType.PEASANT;
			
		case INFANTRY:
			return ModelTile.UnitType.INFANTRY;
			
		case SOLDIER:
			return ModelTile.UnitType.SOLDIER;
			
		case KNIGHT:
			return ModelTile.UnitType.KNIGHT;
			
		case WATCHTOWER:
			return ModelTile.UnitType.WATCHTOWER;
			
		default:
			throw new IllegalArgumentException("Undefined SharedTile.UnitType value "+sharedUT);
		}
	}
	
	public static ModelTile.StructureType translateSharedVillageType(SharedTile.VillageType sharedVT)
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
