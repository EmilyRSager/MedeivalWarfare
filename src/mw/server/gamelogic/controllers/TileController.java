/**
 * @author Charlie Bloomfield
 * March 4, 2015
 */

package mw.server.gamelogic.controllers;

import mw.server.gamelogic.model.Color;
import mw.server.gamelogic.model.Game;
import mw.server.gamelogic.model.GameMap;
import mw.server.gamelogic.model.StructureType;
import mw.server.gamelogic.model.Tile;
import mw.server.gamelogic.model.Unit;
import mw.server.gamelogic.model.UnitType;
import mw.server.gamelogic.model.Village;
import mw.server.gamelogic.model.VillageType;



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
