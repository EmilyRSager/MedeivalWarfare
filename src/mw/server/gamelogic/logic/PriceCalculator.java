package mw.server.gamelogic.logic;

import mw.server.gamelogic.enums.UnitType;
import mw.server.gamelogic.enums.VillageType;
import mw.server.gamelogic.exceptions.CantUpgradeException;
import mw.server.gamelogic.state.Unit;

public class PriceCalculator 
{
	public static int getUnitHireCost(UnitType pUnitType) {
		switch(pUnitType){
			case PEASANT:
				return 10;
			case INFANTRY:
				return 20;
			case SOLDIER:
				return 30;
			default:
				return 40;
		}
	}

	/**
	 * @FIXME DOES NOT Take into account upgrading peasant to soldier, peasant to knight etc. 
	 * @param pUnit
	 * @return
	 * @throws CantUpgradeException
	 */
	public static int getUnitUpgradeCost(UnitType pOldUnitType, UnitType pNewUnitType)
	{
		switch (pNewUnitType) {
			case PEASANT:
				return 10;
			case INFANTRY: 
				return 20; 
			case SOLDIER:
				return 30; 
			case KNIGHT:
			default:
				return 40;  
		}
	}

	/**
	 * Takes in the type of village desired to upgrade to 
	 * @param aVillageType new VillageType
	 * @return the cost
	 * @throws CantUpgradeException
	 */
	public static int getVillageUpgradeCost(VillageType pVillageType){
		switch (pVillageType) 
		{
			case HOVEL:
				return 0;
			case TOWN:
			case FORT:
			default:
				return 8; 
		}
	}

	/**
	 * this will calculate the upkeep cost for a particular village - acts as a helper function to 
	 * assist in admin of each turn
	 * @param pVillage = the village for which the upkeep cost needs to be calculated
	 * @return the amount in gold needed for the upkeep
	 */

	public static int getUpkeepCost(Unit pUnit)
	{
				switch (pUnit.getUnitType()) 
				{
					case PEASANT:
						return 2;
					case INFANTRY:
						return  6;
					case SOLDIER:
						return 18;
					case KNIGHT:
						return 54;
					default:
						return 0; 
				}
	}
}
