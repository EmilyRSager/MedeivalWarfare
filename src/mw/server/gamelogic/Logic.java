package mw.server.gamelogic;

import java.util.ArrayList;
import java.util.Set;


public class Logic {

	public static int getGoldGenerated(GraphNode pGraphNode)
	{
		if (isMeadowOnTile(pGraphNode.getTile()))
		{
			return 2;
		}
		else 
		{
			return 1;
		} 
	}
	private static boolean isMeadowOnTile(Tile pTile)
	{
		return (pTile.getMeadow()); 
	}


	public static void  upgrade(VillageType aVillageType, Village v) throws CantUpgradeException
	{

		VillageType myVillageType = VillageType.NO_VILLAGE; 

		switch (aVillageType) {
		case HOVEL:
			myVillageType =  VillageType.TOWN;
		case TOWN: 
			myVillageType =  VillageType.FORT; 
		case FORT: 
			throw new CantUpgradeException("Village Can't upgrade"); 
		case NO_VILLAGE: 
			throw new CantUpgradeException("Village does not exist"); 
		}

		Set<Tile> myTiles = v.getTiles(); 
		for (Tile t: myTiles)
		{
			if (t.getVillageType() != VillageType.NO_VILLAGE) 
			{
				t.setVillageType(myVillageType);
			}
		}


	}
	public static void clearTombstone(GraphNode pGraphNode)
	{
		Tile pTile = pGraphNode.getTile(); 
		StructureType pStructureType = pTile.getStructureType(); 
		if(pStructureType == StructureType.TOMBSTONE)
		{
			pTile.setStructureType(StructureType.TREE);
		}	
	}
	public static ArrayList<ActionType> getPossibleActions(Unit pUnit, Tile pTile)
	{
		ArrayList< ActionType> possActions = new ArrayList<ActionType>(); 

		UnitType pUnitType = pUnit.getUnitType(); 
		ActionType pActionType = pUnit.getActionType(); 
		{
			//Empty Tile Cases
			if (pTile.getStructureType() == StructureType.NO_STRUCT)
			{
				if (pUnitType == UnitType.PEASANT)
				{
					if (pActionType == ActionType.READY)
					{
						possActions.add(ActionType.BUILDINGROAD);
						possActions.add(ActionType.CULTIVATING_BEGIN);
					}
				}

			}

		}
		return possActions; 

	}
	/**
	 * Does all the logic calculations resulting from moving a unit
	 * @param startTile
	 * @param pDestinationTile
	 * @param pGame
	 * @param pGameMap
	 */
	public static void updateGameState(Unit crtUnit, Tile startTile, Tile pDestinationTile, Game pGame, GameMap pGameMap)
	{
		/*
		  	PossibleGameActions myActions = pGame.tileIsClicked(startTile); 
			Collection<Tile> movableTiles = myActions.getMovableTiles(); 
			if (!movableTiles.contains(pDestinationTile))
			{
				return; 
			}
		 */
		StructureType destStructType = pDestinationTile.getStructureType();
		UnitType crtUnitType = crtUnit.getUnitType(); 
		if (isNeutral(pDestinationTile))
		{
			pGame.takeoverTile(startTile, pDestinationTile);
		} 
		if (tilesAreSameColor(startTile, pDestinationTile))
		{	

			switch (crtUnitType)
			{
			case PEASANT:
				movePeasant(); 
				break;
			case INFANTRY: 
				moveInfantry(); 
				
				break;
			case SOLDIER: 
				moveSoldier(crtUnit, startTile, pDestinationTile, pGameMap);
				break; 
			case KNIGHT:
				moveKnight(crtUnit, startTile, pDestinationTile, pGameMap);

			default:
				break;
			}
		}
	}

	/**
	 * Moves a soldier to a destination tile 
	 * @param crtUnit
	 * @param startTile
	 * @param pDestinationTile
	 * @param pGameMap
	 */
	private static void moveSoldier(Unit crtUnit, Tile startTile, Tile pDestinationTile, GameMap pGameMap)
	{

		switch (pDestinationTile.getStructureType())
		{
		case TREE:
			Village crt = pGameMap.getVillage(startTile);
			if (crt !=null)
			{
				crt.addOrSubtractWood(1);
			}
			pDestinationTile.setUnit(crtUnit);
			pDestinationTile.setStructureType(StructureType.NO_STRUCT);
			startTile.setUnit(null);
			crtUnit.setActionType(ActionType.MOVED);
			break;

		case TOMBSTONE: 
			pDestinationTile.setStructureType(StructureType.NO_STRUCT);
			pDestinationTile.setUnit(crtUnit);
			startTile.setUnit(null);
			crtUnit.setActionType(ActionType.MOVED);
			break; 

		case ROAD: 
			pDestinationTile.setUnit(crtUnit);
			startTile.setUnit(null);
			crtUnit.setActionType(ActionType.READY);
			break; 

		case WATCHTOWER: 
			//TODO
			break;
		case VILLAGE_CAPITAL:
			//TODO 
			break;

		default:
			pDestinationTile.setUnit(crtUnit);
			startTile.setUnit(null);
			crtUnit.setActionType(ActionType.READY);
			if (pDestinationTile.getMeadow())
			{
				pDestinationTile.setHasMeadow(false);
			}
			break;
		}

	}

	/**
	 * Moves a knight to a given tile
	 * @param crtUnit
	 * @param startTile
	 * @param pDestinationTile
	 * @param pGameMap
	 */
	private static void moveKnight(Unit crtUnit, Tile startTile, Tile pDestinationTile, GameMap pGameMap)
	{
		switch (pDestinationTile.getStructureType())
		{
		case TREE:
			//do nothing-- this case shouldn't happen
			break; 
		case TOMBSTONE: 
			//do nothing-- this case shouldn't happen
			break; 
		case ROAD: 
			pDestinationTile.setUnit(crtUnit);
			startTile.setUnit(null);
			crtUnit.setActionType(ActionType.READY);
			break;
		case VILLAGE_CAPITAL: 
			//TODO 
			break; 
		case WATCHTOWER: 
			//TODO 
			break;
		default:
			pDestinationTile.setUnit(crtUnit);
			startTile.setUnit(null);
			crtUnit.setActionType(ActionType.READY);
			if (pDestinationTile.getMeadow())
			{
				pDestinationTile.setHasMeadow(false);
			}
			break;
		}
	}

	private static boolean isNeutral (Tile pTile)
	{
		if (pTile.getColor() == Color.NEUTRAL)
		{
			return true;
		}
		else 
		{
			return false; 
		}
	}
	private static boolean tilesAreSameColor(Tile pTile, Tile pTile2)
	{
		if (pTile.getColor() == pTile2.getColor())
		{
			return true; 
		}
		else 
		{
			return false; 
		}
	}

}
