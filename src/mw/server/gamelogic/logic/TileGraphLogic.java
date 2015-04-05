/**
 * Logic for moving units 
 * @author emilysager
 */
package mw.server.gamelogic.logic;

import java.util.Collection;

import mw.server.gamelogic.enums.ActionType;
import mw.server.gamelogic.enums.Color;
import mw.server.gamelogic.enums.StructureType;
import mw.server.gamelogic.enums.UnitType;
import mw.server.gamelogic.enums.VillageType;
import mw.server.gamelogic.graph.Graph;
import mw.server.gamelogic.state.Tile;
import mw.server.gamelogic.state.Unit;




public final class TileGraphLogic {

	public static boolean isReachableNode(Graph<Tile> pGraph, Tile crtTile, Tile startTile)
	{
		
		Collection<Tile> pCrtNeighbors = pGraph. getNeighbors(crtTile); 
		if (startTile.hasUnit())
		{
			Unit pUnit = startTile.getUnit();
			if (pUnit.getActionType() == ActionType.MOVED)
			{
				System.out.println("[Game] The unit has previously been moved this turn, and cannot move again.");
				return false;
			}
			if (pUnit.getUnitType() == UnitType.CANNON)
			{
				return CannonLogic.isReachableNode(crtTile, startTile, pCrtNeighbors); 
			}
			if (isSeaTile(crtTile))
			{
				return false; 
			}
			if (isWithinVillage(startTile, crtTile))
			{
				return moveUnitWithinVillage(pUnit, crtTile, pCrtNeighbors); 
			}
			if (isNeutralLand(crtTile))
			{
				return moveUnitToNeutralLand(pUnit, crtTile); 
			}
			if (isEnemyTerritory(startTile, crtTile))
			{
				return (moveUnitToEnemyTerritory(pUnit, crtTile, pCrtNeighbors)); 
			}
		}

		return crtTile.equals(startTile); 



	}
	
	public static boolean isPathOver(Graph<Tile> pGraph, Tile startTile, Tile destinationTile)
	{
		if (isNeutralLand(destinationTile)) 
		{
			return true; 
		}
		if (isEnemyTerritory(startTile, destinationTile))
		{
			return true; 
		}
		else 
		{ 
			if (destinationTile.hasUnit())
			{
				return true; 
			}
			if (isTreeOnTile(destinationTile))
			{
				return true; 
			}
			if (isTombstoneOnTile(destinationTile))
			{
				return true; 
			}
			if (isWatchtowerOnTile(destinationTile))
			{
				return true; 
			}
			if (isCapitalOnTile(destinationTile))
			{
				return true; 
			}
			
			return false; 

		}
	}
	
	private static boolean isSeaTile(Tile crtTile) {
		if (crtTile.getColor()== Color.SEATILE)
		{
			return true; 
		}
		return false;
	}
	private static boolean isNeutralLand(Tile crtTile) {
		if (crtTile.getColor() == Color.NEUTRAL)
		{
			return true; 
		}
		return false;
	}
	private static boolean isEnemyTerritory(Tile startTile, Tile crtTile) {
		if (startTile.getColor() != crtTile.getColor() && crtTile.getColor()!=Color.SEATILE && crtTile.getColor()!=Color.NEUTRAL)
		{
			return true; 
		}
		return false;
	}
	private static boolean isWithinVillage(Tile startTile, Tile crtTile) {
		if (startTile.getColor() == crtTile.getColor())
		{
			return true; 
		}
		return false;
	}

	/**
	 * Returns true if a unit can move to a given tile 
	 * @precondition destinationTile must be neutral land	
	 * @param pUnit
	 * @param destinationTile
	 * @return
	 */
	private static boolean moveUnitToNeutralLand(Unit pUnit, Tile destinationTile)
	{

		if (isTreeOnTile(destinationTile))
		{
			return unitCanChopTree(pUnit); 
		}
		return true; 

	}
	/**
	 * Returns true if a unit can move to a given tile
	 * @precondition destinationTile must be in the same village as pUnit
	 * @param pUnit
	 * @param destinationTile
	 * @param destinationNeighbors
	 * @return
	 */
	private static boolean moveUnitWithinVillage(Unit pUnit, Tile destinationTile, Collection<Tile> destinationNeighbors )
	{

		if (destinationTile.hasUnit())
		{
			return false; 
		}
		if (isTreeOnTile(destinationTile))
		{
			return unitCanChopTree(pUnit); 
		}
		if (isTombstoneOnTile(destinationTile))
		{
			return unitCanClearTombstone(pUnit); 
		}
		if (isWatchtowerOnTile(destinationTile))
		{
			return false; 
		}
		if (isCapitalOnTile(destinationTile))
		{
			return false; 
		}
		return true; 

	}
	/**
	 * Returns true if a unit can move to a given tile
	 * @precondition destinationTile must be enemyTerritory	
	 * @param pUnit
	 * @param destinationTile
	 * @param destinationNeighbors
	 * @return
	 */
	private static boolean moveUnitToEnemyTerritory(Unit pUnit, Tile destinationTile, Collection<Tile> destinationNeighbors)
	{
		if (isProtected(pUnit, destinationTile, destinationNeighbors))
		{
			return false; 
		}
		if (destinationTile.hasUnit())
		{
			return unitCanTakeOver(pUnit, destinationTile.getUnit()); 
		}
		if (isTreeOnTile(destinationTile))
		{
			return unitCanChopTree(pUnit); 
		}
		if (isTombstoneOnTile(destinationTile))
		{
			return unitCanClearTombstone(pUnit); 
		}
		if (isCapitalOnTile(destinationTile))
		{
			return canUnitInvadeCapital(pUnit, destinationTile); 
		}
		return true;
	}

	private static boolean canUnitInvadeCapital(Unit pUnit, Tile destinationTile)
	{
		UnitType pUnitType = pUnit.getUnitType(); 
		VillageType dVillageType = destinationTile.getVillageType(); 
		if (pUnitType != UnitType.KNIGHT && pUnitType != UnitType.SOLDIER)
		{
			return false; 
		}
		if (dVillageType == VillageType.HOVEL)
		{
			return true; 
		}
		if (dVillageType == VillageType.TOWN)
		{
			if (pUnitType == UnitType.SOLDIER || pUnitType == UnitType.KNIGHT)
			{
				return true; 
			}

		}
		if (dVillageType == VillageType.FORT)
		{
			if (pUnitType == UnitType.KNIGHT)
			{
				return true; 
			}

		}
		return false; 

	}
	
	private static boolean isProtected(Unit pUnit, Tile destinationTile, Collection<Tile> destinationNeighbors)
	{
		UnitType pUnitType = pUnit.getUnitType(); 
		for (Tile lTile : destinationNeighbors)
		{
			Unit lUnit = lTile.getUnit();
			if (lUnit != null)
			{
				if(lUnit.getUnitType().ordinal() >= pUnitType.ordinal()) 
				{
					return false;
				}
			}
		}
		if (isWatchtowerGuardingTile(destinationTile, destinationNeighbors))
		{
			switch (pUnitType)
			{
			case SOLDIER: 
				return false; 
			case KNIGHT: 
				return false; 
			default: 
				break; 
			} 
		}
		return true; 
	}
	private static boolean unitCanTakeOver(Unit crtUnit, Unit enemyUnit)
	{
		UnitType crtType = crtUnit.getUnitType();
		UnitType enemyType = enemyUnit.getUnitType();

		if (crtType.ordinal() > enemyType.ordinal() )
		{
			return true; 
		}
		return false; 
	}	
	private static boolean unitCanChopTree(Unit pUnit)
	{
		UnitType pUnitType = pUnit.getUnitType(); 
		if (pUnitType == UnitType.KNIGHT) 
		{
			return false; 
		}
		return true;
	}

	private static boolean unitCanClearTombstone(Unit pUnit)
	{
		UnitType pUnitType = pUnit.getUnitType(); 
		if (pUnitType != UnitType.KNIGHT) 
		{
			return false; 
		}
		return true; 
	}

	private static boolean isCapitalOnTile(Tile pTile)
	{
		if (pTile.getStructureType() == StructureType.VILLAGE_CAPITAL)
		{
			return true; 
		}
		return false; 
	}
	private static boolean isWatchtowerOnTile(Tile pTile)
	{
		if (pTile.getStructureType() == StructureType.WATCHTOWER)
		{
			return true; 
		}
		return false; 
	}
	private static boolean isWatchtowerGuardingTile(Tile pTile, Collection<Tile> pNeighbors)
	{
		if (pTile.getStructureType() == StructureType.WATCHTOWER)
		{
			return true; 
		}
		if (pTile.getStructureType() != StructureType.WATCHTOWER)
		{
			for (Tile lTile : pNeighbors)
			{
				if (lTile.getStructureType() == StructureType.WATCHTOWER)
				{
					return true; 
				}
			}
		}
		return false; 
	}
	private static boolean isTombstoneOnTile(Tile pTile)
	{
		if (pTile.getStructureType() == StructureType.TOMBSTONE)
		{
			return true; 
		}
		return false;
	}
	private static boolean isTreeOnTile(Tile pTile)
	{
		if (pTile.getStructureType() == StructureType.TREE)
		{
			return true; 
		}
		return false; 
	}
	public static boolean isVillageBoundary(Graph<Tile> pGraph, Tile pStartTile, Tile pCrtTile)
	{
		Collection<Tile> lNeighbors = pGraph.getNeighbors(pCrtTile);
		boolean areAllNeighborsSameColor = true; 
		for (Tile lTile : lNeighbors)
		{
			if (!(lTile.getColor() == pStartTile.getColor()))
			{
				areAllNeighborsSameColor = false; 
			}
		}
		return areAllNeighborsSameColor; 

	}
	public static boolean tilesAreSameColor(Tile t1, Tile t2)
	{
		return t1.getColor() == t2.getColor();
	}
}


