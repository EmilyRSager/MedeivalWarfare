package mw.server.gamelogic;

import java.util.Collection;

public class PriceCalculator 
{
	public static int getHireCost(UnitType pUnitType){
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
	 * @FIXME
	 * @param pUnit
	 * @return
	 * @throws CantUpgradeException
	 */
	public static int getUpgradePrice(Unit pUnit) throws CantUpgradeException
	{
		UnitType pUnitType= pUnit.getUnitType();
		switch (pUnitType) {
		case PEASANT:
			return 20;
		case INFANTRY: 
			return 30; 
		case SOLDIER:
			return 40; 
		case KNIGHT:
			throw new CantUpgradeException("Unit Can't Upgrade"); 
		default:
			return 10;  
		}
	}
	
	public static int getUpgradePrice(VillageType aVillageType) throws CantUpgradeException
	{
		switch (aVillageType) {
		case HOVEL:
			return 8; 
		case TOWN:
			return 8; 
		case FORT:
			throw new CantUpgradeException("Village Can't be Upgraded"); 
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
	
	public static int upkeepCostCalculator(Village pVillage){
		int totalUpkeepCost = 0;
		Collection<GraphNode> lGraphNodesOfVillageCollection = pVillage.getVillageNodes();
		for(GraphNode lNode: lGraphNodesOfVillageCollection){
			Tile lTile = lNode.getTile();
			Unit lUnit = lTile.getUnit();
			
			if (lUnit!=null) {
				switch (lUnit.getUnitType()) {
				case PEASANT:
					totalUpkeepCost +=2;
					break;

				case INFANTRY:
					totalUpkeepCost += 6;
					break;
				
				case SOLDIER:
					totalUpkeepCost += 18;
					break;
					
				case KNIGHT:
					totalUpkeepCost += 54;
					break;
					
				default:
					break;
				}
			}
			
		}
		
		return totalUpkeepCost;
	}
}
