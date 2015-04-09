package mw.server.network.translators;


import mw.server.gamelogic.enums.ActionType;
import mw.server.gamelogic.enums.UnitType;
import mw.server.gamelogic.enums.VillageType;

import mw.shared.SharedActionType;
import mw.shared.SharedTile;

public abstract class NetworkToModelTranslator {

	/* ========================
	 * 		Static methods
	 * ========================
	 */
	
	public static VillageType translateVillageType(SharedTile.VillageType sharedVT)
	{
		switch (sharedVT)
		{
		case NONE:
			return VillageType.NO_VILLAGE;
			
		case HOVEL:
			return VillageType.HOVEL;
			
		case TOWN:
			return VillageType.TOWN;
			
		case FORT:
			return VillageType.FORT;
			
			default:
				throw new IllegalArgumentException("Invalid value to translate to VillageType : "+sharedVT);
		}
	}

	public static UnitType translateUnitType(SharedTile.UnitType sharedUT)
	{
		switch(sharedUT)
		{
		case NONE:
			return UnitType.NO_UNIT;
			
		case PEASANT:
			return UnitType.PEASANT;
			
		case INFANTRY:
			return UnitType.INFANTRY;
			
		case SOLDIER:
			return UnitType.SOLDIER;
			
		case KNIGHT:
			return UnitType.KNIGHT;
			
		case CANNON:
			return UnitType.CANNON;
			
			default:
				throw new IllegalArgumentException("Invalid value to translate to UnitType : "+sharedUT);
		}
	}
	
	public static ActionType translateActionType(SharedActionType sharedAT)
	{
		switch(sharedAT)
		{
		case BUILD_ROAD:
			return ActionType.BUILDINGROAD;
			
		case CULTIVATE_MEADOW:
			return ActionType.CULTIVATING_BEGIN;
			
			default:
				throw new IllegalArgumentException("Invalid value to translate to ActionType : "+sharedAT);
		}
	}
	
}