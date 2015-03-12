/**
 * @author Charlie Bloomfield
 * March 4, 2015
 */

package mw.server.gamelogic.controllers;

import mw.server.gamelogic.enums.Color;
import mw.server.gamelogic.enums.StructureType;
import mw.server.gamelogic.enums.UnitType;
import mw.server.gamelogic.enums.VillageType;
import mw.server.gamelogic.state.Game;
import mw.server.gamelogic.state.GameMap;
import mw.server.gamelogic.state.Tile;
import mw.server.gamelogic.state.Unit;
import mw.server.gamelogic.state.Village;



public class TileController {

	/**
	 * @param pTile
	 * @return
	 */
	public static int getGold(Tile pTile, Game pGame){
		if (pTile.getVillageType()!=VillageType.NO_VILLAGE)
		{
			GameMap crtMap = pGame.getGameMap();  
			Village crtVillage = crtMap.getVillage(pTile); 
			int gold = crtVillage.getGold(); 
			return gold; 
		}
		else
		{
			return 0;
		}
	}

	/**
	 * @param pTile
	 * @return
	 */
	public static int getWood(Tile pTile, Game pGame)
	{
		if (pTile.getVillageType()!=VillageType.NO_VILLAGE)
		{
			GameMap crtMap = pGame.getGameMap(); 
			Village crtVillage = crtMap.getVillage(pTile); 
			int wood = crtVillage.getWood(); 
			return wood; 
		}
		else
		{
			return 0;
		}

	}

	/**
	 * @param pTile
	 * @return
	 */
	public static UnitType getUnitType(Tile pTile) {
		//TODO throw exception
		Unit lUnit = pTile.getUnit();
		if(lUnit != null){			
			return pTile.getUnit().getUnitType();
		}
		
		return UnitType.NO_UNIT;
	}

	/**
	 * @param pTile
	 * @return
	 */
	public static int[] getCoordinates(Tile pTile){
		return pTile.getTileCoordinates();
	}

	/**
	 * @param pTile
	 * @return
	 */
	public static VillageType getVillageType(Tile pTile){
		return pTile.getVillageType();
	}

	/**
	 * @param pTile
	 * @return
	 */
	public static Color getColor(Tile pTile){
		return pTile.getColor();
	}

	/**
	 * 
	 * @param pTile
	 * @return
	 */
	public static StructureType getStructureType(Tile pTile){
		return pTile.getStructureType();
	}

	/**
	 * @param pTile
	 * @return
	 */
	public static boolean hasRoad(Tile pTile){
		return pTile.getStructureType() == StructureType.ROAD;
	}
}
