package mw.server.gamelogic.logic;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import mw.server.gamelogic.enums.ActionType;
import mw.server.gamelogic.enums.StructureType;
import mw.server.gamelogic.enums.UnitType;
import mw.server.gamelogic.enums.VillageType;
import mw.server.gamelogic.exceptions.CantUpgradeException;
import mw.server.gamelogic.state.Game;
import mw.server.gamelogic.state.Tile;
import mw.server.gamelogic.state.Unit;
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
	public static ArrayList<UnitType> getVillagerHireOrUpgradeTypes(Tile pTile, Game pGame) 
	{
		
		Village lVillage = pGame.getVillage(pTile);
		VillageType lVillageType = lVillage.getVillageType();

		ArrayList<UnitType> rArray = new ArrayList<UnitType>();
		if (pTile.getStructureType()!= StructureType.TREE 
				&& pTile.getStructureType()!=StructureType.TOMBSTONE && pTile.getStructureType()!=StructureType.WATCHTOWER)
		{
		
			if (pTile.getUnit()==(null))
			{
				
				
				switch (lVillageType) 
				{
				case HOVEL: 
					rArray.add(UnitType.PEASANT); 
					rArray.add(UnitType.INFANTRY); 
					break;
				case TOWN: 
					rArray.add(UnitType.PEASANT); 
					rArray.add(UnitType.SOLDIER); 
					rArray.add(UnitType.INFANTRY); 
					break;
				case FORT: 
					rArray.add(UnitType.INFANTRY); 
					rArray.add(UnitType.KNIGHT); 
					rArray.add(UnitType.PEASANT); 
					rArray.add(UnitType.SOLDIER); 
					rArray.add(UnitType.CANNON);
				case CASTLE: 
					rArray.add(UnitType.INFANTRY); 
					rArray.add(UnitType.KNIGHT); 
					rArray.add(UnitType.PEASANT); 
					rArray.add(UnitType.SOLDIER); 
					rArray.add(UnitType.CANNON);
				default: 
					break; 
				}
			}
			else if (pTile.getUnit().getActionType() == ActionType.READY)
			{

				if (pTile.getUnit().getUnitType() == UnitType.PEASANT)
				{

					rArray.add(UnitType.INFANTRY);
					if (lVillageType == VillageType.TOWN ||lVillageType == VillageType.FORT)
					{
						rArray.add(UnitType.SOLDIER); 
					}
					if (lVillageType == VillageType.FORT)
					{
						rArray.add(UnitType.KNIGHT);
					}
				}
				if (pTile.getUnit().getUnitType().equals(UnitType.INFANTRY))
				{
					if (lVillageType == VillageType.TOWN ||lVillageType == VillageType.FORT)
					{
						rArray.add(UnitType.SOLDIER); 
					}
					if (lVillageType == VillageType.FORT)
					{
						rArray.add(UnitType.KNIGHT);
					}
				}
				if (pTile.getUnit().getUnitType().equals(UnitType.SOLDIER))
				{
					if (lVillageType == VillageType.FORT)
					{
						rArray.add(UnitType.KNIGHT); 
					}
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
			possVillageUpgradeType = VillageType.CASTLE;  
			break;
		default:
			possVillageUpgradeType = VillageType.NO_VILLAGE; 
			break;
		}
		return possVillageUpgradeType; 
	}

	public static boolean canBuildWatchtower(Tile pTile, Game pGame) 
	{
		
		Village lVillage =  pGame.getVillage(pTile);
		VillageType pVillageType = lVillage.getVillageType();
		if (!pTile.equals(lVillage.getCapital()))
		{
			return false;
		}
		if (pVillageType != VillageType.TOWN && pVillageType !=  VillageType.FORT) 
		{
			System.out.println("[Game] can build watchtower is false");
			return false; 
		}
		System.out.println("[Game] can build watchtower is true");
		return true;
	}

	public static Collection<Tile> getCombinableUnitTiles(Tile startTile, Game pGame) 
	{
		Collection<Tile>rCombinable = new HashSet<Tile>(); 
		Village startVillage = pGame.getVillage(startTile); 
		for (Tile lTile: startVillage.getTiles())
		{
			if (lTile.hasUnit())
			{
				Unit lUnit = lTile.getUnit(); 
				if (lUnit.getUnitType() != UnitType.KNIGHT && lUnit.getUnitType() != UnitType.CANNON)
				{
					if (lUnit.getActionType() == ActionType.READY)
					{
						rCombinable.add(lTile); 
					}
				}
			}
		}
		return rCombinable; 
	}
}
