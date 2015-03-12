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
import mw.shared.SharedCoordinates;
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
						TileController.hasRoad(pTile)
						),
						
				TileController.hasRoad(pTile),
				translateUnitType(TileController.getUnitType(pTile)),
				translateVillageType(TileController.getVillageType(pTile)),
				TileController.getGold(pTile, pGame),
				TileController.getWood(pTile, pGame)
				);
		return lSharedTile;
	}
	
	/**
	 * @param pVillageType
	 * @return
	 */

	public static VillageType translateVillageType(mw.server.gamelogic.enums.VillageType pVillageType){
		if(pVillageType == mw.server.gamelogic.enums.VillageType.HOVEL){
			return VillageType.HOVEL;
		}
		
		else if(pVillageType == mw.server.gamelogic.enums.VillageType.TOWN){
			return VillageType.TOWN;
		}
		
		else if(pVillageType == mw.server.gamelogic.enums.VillageType.FORT){

			return VillageType.FORT;
		}
		
		else{
			return VillageType.NONE;
		}
	}
	
	/**
	 * @param pUnitType
	 * @return SharedTile representation of the tile's occupying Unit
	 */
	public static SharedTile.UnitType translateUnitType(UnitType pUnitType){
		
		if(pUnitType == mw.server.gamelogic.enums.UnitType.NO_UNIT){
			return SharedTile.UnitType.NONE;
		}
		
		else if(pUnitType == mw.server.gamelogic.enums.UnitType.PEASANT){
			return SharedTile.UnitType.PEASANT;
		}
		
		else if (pUnitType == mw.server.gamelogic.enums.UnitType.INFANTRY){
			return SharedTile.UnitType.INFANTRY;
		}
		
		else if(pUnitType == mw.server.gamelogic.enums.UnitType.SOLDIER){
			return SharedTile.UnitType.SOLDIER;
		}
		
		else if(pUnitType == mw.server.gamelogic.enums.UnitType.KNIGHT){
			
			return SharedTile.UnitType.KNIGHT;
		}
		
		else {
			return SharedTile.UnitType.WATCHTOWER;
		}
	}
	
	/**
	 * @param pStructureType, pHasMeadow
	 * @return 
	 */
	public static SharedTile.Terrain translateTerrain(StructureType pStructureType, boolean pHasMeadow){
		if(pStructureType == StructureType.NO_STRUCT){
			return Terrain.GRASS;
		}
		
		else if(pStructureType == StructureType.TREE){
			return Terrain.TREE;
		}
		
		else if(pHasMeadow){
			return Terrain.MEADOW;
		}
		
		else if(pStructureType == StructureType.TOMBSTONE){
			return Terrain.TOMBSTONE;
		}
		
		else if(pStructureType == StructureType.VILLAGE_CAPITAL){
			return SharedTile.Terrain.GRASS;
		}
		
		else{
			return Terrain.SEA;
		}
	}
	
	/**
	 * 
	 * @param pCoordinates
	 * @return
	 */
	public static SharedCoordinates translateCoordinates(int[] pCoordinates){
		return new SharedCoordinates(pCoordinates[0], pCoordinates[1]);
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
