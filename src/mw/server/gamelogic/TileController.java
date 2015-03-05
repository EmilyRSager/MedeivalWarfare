/**
 * @author Charlie Bloomfield
 * March 4, 2015
 */

package mw.server.gamelogic;

public class TileController {
	
	/**
	 * 
	 * @param pTile
	 * @return
	 */
	public static int getGold(Tile pTile){
		return 0;
	}
	
	/**
	 * 
	 * @param pTile
	 * @return
	 */
	public static int getWood(Tile pTile){
		return 0;
	}
	
	/**
	 * @param pTile
	 * @return
	 */
	public static UnitType getUnitType(Tile pTile){
		return pTile.getUnit().getUnitType();
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
