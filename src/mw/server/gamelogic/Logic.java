package mw.server.gamelogic;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;


public class Logic {

	public static int getGoldGenerated(GraphNode pGraphNode)
	{
		if (isMeadowOnTile(pGraphNode.getTile()))
		{
			return 2;
		}
		else {
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
		PossibleActions myActions = pGame.tileIsClicked(startTile); 
		Collection<Tile> movableTiles = myActions.getMovableTiles(); 
		StructureType destStructType = pDestinationTile.getStructureType();
		UnitType crtUnitType = crtUnit.getUnitType(); 
		if (!movableTiles.contains(pDestinationTile))
		{
			return; 
		}
		else
		{
			if (isNeutral(pDestinationTile))
			{
				pGame.takeoverTile(startTile, pDestinationTile);
			}
		}
		if (tilesAreSameColor(startTile, pDestinationTile))
		{	

			switch (crtUnitType)
			{
			case PEASANT:
				switch (destStructType)
				{

				case TREE:
					Village crt = pGameMap.getVillage(startTile);
					if (crt !=null)
					{
						crt.addOrSubtractWood(1);
					}
					pDestinationTile.setUnit(crtUnit);
					startTile.setUnit(null);
					crtUnit.setActionType(ActionType.MOVED);
					break;

				case TOMBSTONE: 
					pDestinationTile.setStructureType(StructureType.NO_STRUCT);
					pDestinationTile.setUnit(crtUnit);
					startTile.setUnit(null);
					crtUnit.setActionType(ActionType.MOVED);
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
					crtUnit.setActionType(ActionType.READY); //If units move to a road or empty tile they can still move
					break;
				}

				break;
			case INFANTRY: 
				switch (destStructType)
				{
				case TREE: 
					Village crt = pGameMap.getVillage(startTile);
					if (crt !=null)
					{
						crt.addOrSubtractWood(1);
					}
					pDestinationTile.setUnit(crtUnit);
					startTile.setUnit(null);
					crtUnit.setActionType(ActionType.MOVED);
					break;
				case TOMBSTONE: 
					pDestinationTile.setUnit(crtUnit);
					pDestinationTile.setStructureType(StructureType.NO_STRUCT);
					startTile.setUnit(null);
					crtUnit.setActionType(ActionType.MOVED);
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
				break;
			case SOLDIER: 
				switch (destStructType)
				{

				case TREE:
					Village crt = pGameMap.getVillage(startTile);
					if (crt !=null)
					{
						crt.addOrSubtractWood(1);
					}
					pDestinationTile.setUnit(crtUnit);
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
				break; 
			case KNIGHT:
				switch (destStructType)
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
				break;

			default:
				break;
			}
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
