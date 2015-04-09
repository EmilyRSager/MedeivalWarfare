/**
 * @author Charlie Bloomfield
 * Feb 25, 2015
 */

package mw.server.network.translators;

import mw.server.gamelogic.controllers.TileController;

import mw.server.gamelogic.enums.Color;
import mw.server.gamelogic.enums.StructureType;
import mw.server.gamelogic.enums.UnitType;
import mw.server.gamelogic.state.Game;
import mw.server.gamelogic.state.Tile;

import mw.shared.SharedColor;
import mw.shared.Coordinates;
import mw.shared.SharedTile;
import mw.shared.SharedTile.Terrain;
import mw.shared.SharedTile.VillageType;

public class SharedTileTranslator {
	
	/**
	 * @param pGameMap
	 * @return SharedTile representation of pGameMap
	 */
	public static SharedTile[][] translateMap(Tile[][] pGameMap, Game pGame){
		SharedTile[][] lGameMap = new SharedTile[pGameMap.length][pGameMap[0].length];
		for(int row = 0; row < pGameMap.length; row++){
			for(int col = 0; col < pGameMap[0].length; col++){
				lGameMap[row][col] = translateTile(pGameMap[row][col], pGame);
			}
		}

		return lGameMap;
	}

	/**
	 * @param pTile
	 * @return SharedTile encoding of pTile
	 */
	public static SharedTile translateTile(Tile pTile, Game pGame){
		SharedTile lSharedTile = new SharedTile(
				translateColor(TileController.getColor(pTile)),
				translateCoordinates(TileController.getCoordinates(pTile)),
				translateTerrain(
						TileController.getStructureType(pTile),
						TileController.hasMeadow(pTile),
						TileController.getColor(pTile)
						),
						
				TileController.hasRoad(pTile),
				translateUnitType(TileController.getUnitType(pTile), TileController.getStructureType(pTile)),
				translateVillageType(TileController.getVillageType(pTile)),
				TileController.getGold(pTile, pGame),
				TileController.getWood(pTile, pGame)
				);
		System.out.println("[SharedTileTranslator] Unit  Type is" + lSharedTile.getUnitType());
		return lSharedTile;
	}
	
	/**
	 * @param pVillageType
	 * @return
	 */

	public static VillageType translateVillageType(mw.server.gamelogic.enums.VillageType pVillageType){
		switch(pVillageType)
		{
		case NO_VILLAGE:
			return VillageType.NONE;
			
		case HOVEL:
			return VillageType.HOVEL;
			
		case TOWN:
			return VillageType.TOWN;
			
		case FORT:
			return VillageType.FORT;
			
		case CASTLE:
			return VillageType.CASTLE;
			
		default:
			throw new IllegalArgumentException("The value "+pVillageType+" is not a valid value to translate to shared VillageType");
		}
	}
	
	/**
	 * @param pUnitType
	 * @return SharedTile representation of the tile's occupying Unit
	 */
	public static SharedTile.UnitType translateUnitType(UnitType pUnitType, StructureType structType){
		if (structType == StructureType.WATCHTOWER)
		{
			return SharedTile.UnitType.WATCHTOWER;
		}	
		switch(pUnitType)
		{
		case NO_UNIT:
			return SharedTile.UnitType.NONE;
			
		case PEASANT:
			return SharedTile.UnitType.PEASANT;
			
		case INFANTRY:
			return SharedTile.UnitType.INFANTRY;
			
		case SOLDIER:
			return SharedTile.UnitType.SOLDIER;
			
		case KNIGHT:
			return SharedTile.UnitType.KNIGHT;
			
		case CANNON:
			return SharedTile.UnitType.CANNON;
			
		default:
			throw new IllegalArgumentException("Value "+pUnitType+" is not a valid value to translate to SharedTile.UnitType");
		}
	}
	
	/**
	 * @param pStructureType, pHasMeadow
	 * @return 
	 */
	public static SharedTile.Terrain translateTerrain(StructureType pStructureType, boolean pHasMeadow, Color pColor){
		if(pColor == Color.SEATILE){
			return Terrain.SEA;
		}
		
		else if(pHasMeadow){
			return Terrain.MEADOW;
		}
		
		else if(pStructureType == StructureType.NO_STRUCT 
				|| pStructureType == StructureType.ROAD)
		{
			return Terrain.GRASS;
		}
		
		else if(pStructureType == StructureType.TREE){
			return Terrain.TREE;
		}
		
		else if(pStructureType == StructureType.TOMBSTONE){
			return Terrain.TOMBSTONE;
		}
		
		else if(pStructureType == StructureType.VILLAGE_CAPITAL){
			return SharedTile.Terrain.GRASS;
		}
		
		else{
			throw new IllegalArgumentException("The tuple "+pStructureType+","+pHasMeadow+","+pColor+" is not a valid tuple to be translated to SharedTile.Terrain");
		}
	}
	
	/**
	 * 
	 * @param coordinates
	 * @return
	 */
	public static Coordinates translateCoordinates(Coordinates coordinates){
		return coordinates;
	}

	/**
	 * @param pColor
	 * @return
	 */
	public static SharedColor translateColor(Color pColor){
		SharedColor lSharedColor;
		switch(pColor) {
			case GREEN: 
				lSharedColor = SharedColor.GREEN;
				break;
			case RED: 
				lSharedColor = SharedColor.RED;
				break;
			case BLUE: 
				lSharedColor = SharedColor.BLUE;
				break;
			case YELLOW:
				lSharedColor = SharedColor.YELLOW;
				break;
			default:
				lSharedColor = SharedColor.GREY;
				break;
		}

		return lSharedColor;
	}
}
