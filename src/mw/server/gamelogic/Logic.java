package mw.server.gamelogic;

import java.util.ArrayList;
import java.util.Collection;

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
	
	
	public static VillageType upgrade(VillageType aVillageType) throws CantUpgradeException
	{
		switch (aVillageType) {
		case HOVEL:
			
			return VillageType.TOWN;
		case TOWN: 
			return VillageType.FORT; 
		case FORT: 
			throw new CantUpgradeException("Village Can't upgrade"); 
		case NO_VILLAGE: 
			throw new CantUpgradeException("Village does not exist"); 
		}
		return VillageType.HOVEL;
		
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
	public static void updateGameState(Tile startTile, Tile pDestinationTile, Game pGame, GameMap pGameMap)
	{
		CollectionOfPossibleActions myActions = pGame.tileIsClicked(startTile); 
		Collection<Tile> movableTiles = myActions.getMovableTiles(); 
		if (!movableTiles.contains(pDestinationTile))
		{
			return; 
		}
		else{
			if (isNeutral(pDestinationTile))
			{
				pGame.takeoverTile(startTile, pDestinationTile);
			}
			if (tilesAreSameColor(startTile, pDestinationTile))
			{
				StructureType pStructureType = pDestinationTile.getStructureType();
				if (pStructureType == StructureType.TREE)
				{
					Village crt = pGameMap.getVillage(startTile);
					if (crt !=null)
					{
					crt.addWood(1);
					}
				}
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
