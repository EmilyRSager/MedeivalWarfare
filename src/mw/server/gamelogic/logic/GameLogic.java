package mw.server.gamelogic.logic;

import java.util.ArrayList;
import java.util.Collection;

import mw.server.gamelogic.enums.StructureType;
import mw.server.gamelogic.enums.UnitType;
import mw.server.gamelogic.enums.VillageType;
import mw.server.gamelogic.exceptions.CantUpgradeException;
import mw.server.gamelogic.state.Game;
import mw.server.gamelogic.state.Tile;
import mw.server.gamelogic.state.Village;

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
	public static ArrayList<UnitType> getVillagerHireOrUpgradeTypes(Tile pTile) 
	{
		ArrayList<UnitType> rArray = new ArrayList<UnitType>();
		if (pTile.getStructureType()!= StructureType.TREE && pTile.getStructureType()!=StructureType.VILLAGE_CAPITAL
				&& pTile.getStructureType()!=StructureType.TOMBSTONE && pTile.getStructureType()!=StructureType.WATCHTOWER)
		{
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

	public static boolean canBuildWatchtower(Tile pTile, Game pGame) 
	{
		Collection <Tile> lNeighbors = pGame.getNeighbors(pTile);
		Village lVillage =  pGame.getVillage(pTile);
		int lVillageWood = lVillage.getWood();
		VillageType pVillageType =lVillage.getVillageType();
		if (lVillageWood < 5)
		{
			return false; 
		}
		if (pVillageType != VillageType.TOWN && pVillageType !=  VillageType.FORT) 
		{
			return false; 
		}
		if(pTile.hasUnit())
		{
			return false; 
		}
		for (Tile lTile : lNeighbors)
		{
			if (lTile.getStructureType() !=StructureType.NO_STRUCT)
			{
				return false; 
			}
		}
		return true;
	}
}
