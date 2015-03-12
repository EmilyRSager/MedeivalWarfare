package mw.server.gamelogic.logic;

import java.util.ArrayList;

import mw.server.gamelogic.enums.UnitType;
import mw.server.gamelogic.enums.VillageType;
import mw.server.gamelogic.exceptions.CantUpgradeException;
import mw.server.gamelogic.state.Tile;

/**
 * 
 * @author emilysager
 *
 */
public class GameLogic 
{

	/**
	 * @param pTile
	 * @return
	 * @throws CantUpgradeException
	 * returns the list of units you can upgrade to, doesn't actually hire a villager 
	 */
	public static ArrayList<UnitType> wantToHireVillager(Tile pTile) 
	{
		ArrayList<UnitType> rArray = new ArrayList<UnitType>();

		if (pTile.getUnit()==(null))
		{
			rArray.add(UnitType.INFANTRY); 
			rArray.add(UnitType.KNIGHT); 
			rArray.add(UnitType.PEASANT); 
			rArray.add(UnitType.SOLDIER); 			
		}
		else 
		{
			if (pTile.getUnit().getUnitType().equals(UnitType.PEASANT))
			{
				rArray.add(UnitType.INFANTRY);
				rArray.add(UnitType.SOLDIER); 
				rArray.add(UnitType.KNIGHT);
			}
			if (pTile.getUnit().getUnitType().equals(UnitType.INFANTRY))
			{
				rArray.add(UnitType.SOLDIER); 
				rArray.add(UnitType.KNIGHT);
			}
			if (pTile.getUnit().getUnitType().equals(UnitType.SOLDIER))
			{
				rArray.add(UnitType.KNIGHT); 
			}
			if (pTile.getUnit().getUnitType().equals(UnitType.KNIGHT)) 
			{
				return null; 
			}
		}
		return rArray; 
	}

	public static VillageType getPossibleVillageUpgrades(VillageType pVillageType)
	{
		VillageType possVillageUpgradeType; 
		switch (pVillageType)
		{
		case HOVEL:
			possVillageUpgradeType = VillageType.TOWN;
			break;
		case TOWN: 
			possVillageUpgradeType = VillageType.FORT;
			break; 
		case FORT: 
			possVillageUpgradeType = null;  
			break;
		default:
			possVillageUpgradeType = VillageType.NO_VILLAGE; 
			break;
		}
		return possVillageUpgradeType; 
	}
}
