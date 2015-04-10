package mw.server.gamelogic.logic;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import mw.server.gamelogic.enums.UnitType;
import mw.server.gamelogic.enums.VillageType;
import mw.server.gamelogic.state.Game;
import mw.server.gamelogic.state.Tile;


public class UnitHireLogic {

	public static Collection<Tile> wantToHireUnit(Tile pTile, Game pGame)
	{
		Collection<Tile> rTile = new HashSet<Tile>(); 
		for (Tile lTile : pGame.getVillage(pTile).getTiles())
		{
			if (lTile.isEmpty()) 
			{
				rTile.add(lTile);
			}
	
		} 
		return rTile;
	}
	
	public static UnitType unitCombinationResult(UnitType ut1, UnitType ut2) {
		switch (ut1)
		{
		case PEASANT: 
			switch (ut2) 
			{
			case PEASANT:
				return UnitType.INFANTRY;
			case INFANTRY: 
				return UnitType.SOLDIER; 
			case SOLDIER: 
				return UnitType.KNIGHT;
			default:
				break;
			}
			break; 
		case INFANTRY: 
			switch (ut2) 
			{
			case PEASANT:
				return UnitType.SOLDIER;
			case INFANTRY: 
				return UnitType.KNIGHT; 
			default:
				break;
			}
			break; 
		case SOLDIER: 
			switch (ut2) 
			{
			case PEASANT:
				return UnitType.KNIGHT;
			default:
				break;
			}
		default:
			break;
		}
		
		throw new IllegalArgumentException("The unit types "+ut1+" and "+ut2+" can't be combined");
	}
	
	public static Collection<UnitType> getManageableUnitTypes(VillageType vType)
	{
		ArrayList<UnitType> result = new ArrayList<UnitType>();
		switch (vType) 
		{
		case HOVEL: 
			result.add(UnitType.PEASANT); 
			result.add(UnitType.INFANTRY); 
			break;
		case TOWN: 
			result.add(UnitType.PEASANT); 
			result.add(UnitType.SOLDIER); 
			result.add(UnitType.INFANTRY); 
			break;
		case FORT: 
			result.add(UnitType.INFANTRY); 
			result.add(UnitType.KNIGHT); 
			result.add(UnitType.PEASANT); 
			result.add(UnitType.SOLDIER); 
			result.add(UnitType.CANNON);
		case CASTLE: 
			result.add(UnitType.INFANTRY); 
			result.add(UnitType.KNIGHT); 
			result.add(UnitType.PEASANT); 
			result.add(UnitType.SOLDIER); 
			result.add(UnitType.CANNON);
		default: 
			throw new IllegalArgumentException("Unrecognized village type "+vType);
		}
		return result;
	}
	
}
