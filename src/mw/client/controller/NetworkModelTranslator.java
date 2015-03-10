package mw.client.controller;

import mw.client.model.Coordinates;
import mw.client.model.ModelTile;
import mw.client.model.ModelTile.StructureType;
import mw.shared.SharedCoordinates;
import mw.shared.SharedTile;

/**
 * The NetworkModelTranslator is used to translate information from the network to a format that the Model can handle.
 * @author Hugo Kapp
 *
 */
public final class NetworkModelTranslator {

	/**
	 * Translates the given SharedTile to a ModelTile
	 * @param sharedTile the SharedTile to translate
	 * @return a translation of the given sharedTile
	 */
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
		if (sharedTile.getVillage() != SharedTile.VillageType.NONE) {
			newTile.setVillageGold(sharedTile.getVillageGold());
			newTile.setVillageWood(sharedTile.getVillageWood());
		}
		return newTile;
	}
	
	/**
	 * Translates the given SharedCoordinates to Model Coordinates
	 * @param sharedCoord the SharedCoordinates to be translated
	 * @return a Model Coordinates that represents the given sharedCoord
	 */
	public static Coordinates translateSharedCoordinates(SharedCoordinates sharedCoord)
	{
		return new Coordinates(sharedCoord.getX(),sharedCoord.getY());
	}
	
	/**
	 * Translates the given shared Terrain to a model-useable Terrain
	 * @param sharedTerrain the shared Terrain to translate
	 * @return a model Terrain that is the translation of the given shared Terrain
	 */
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
	
	/**
	 * Translates the given shared UnitType to a model UnitType
	 * @param sharedUT the shared UnitType to translate
	 * @return a model UnitType which represents the given shared UnitType
	 */
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
	
	/**
	 * Translates the given shared VillageType to a model VillageType
	 * @param sharedVT the shared VillageType to translate
	 * @return the translation of the given shared VillageType to a model VillageType
	 */
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
