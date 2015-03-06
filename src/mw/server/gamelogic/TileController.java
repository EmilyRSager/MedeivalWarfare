/**
 * @author Charlie Bloomfield
 * March 4, 2015
 */

package mw.server.gamelogic;

import mw.shared.SharedTile;

public class TileController {

	/**
	 * 
	 * @param pTile
	 * @return
	 */
	public static int getGold(Tile pTile, Game pGame){
		if (pTile.getVillageType()!=VillageType.NO_VILLAGE)
		{
			GameMap crtMap = pGame.getGameMap();  
			Village crtVillage = crtMap.getVillage(pTile); 
			int gold = crtVillage.getAGold(); 
			return gold; 
		}
		else
		{
			return 0;
		}
	}

	/**
	 * 
	 * @param pTile
	 * @return
	 */
	public static int getWood(Tile pTile, Game pGame)
	{
		if (pTile.getVillageType()!=VillageType.NO_VILLAGE)
		{
			GameMap crtMap = pGame.getGameMap(); 
			Village crtVillage = crtMap.getVillage(pTile); 
			int wood = crtVillage.getAWood(); 
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
