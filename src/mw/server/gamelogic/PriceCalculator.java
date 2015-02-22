package mw.server.gamelogic;

public class PriceCalculator 
{
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
			throw new CantUpgradeException("Village Can't Upgrade"); 
		default:
			return 8; 
		}
	}
	

}
