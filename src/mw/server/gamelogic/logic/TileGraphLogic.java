/**
 * Logic for moving units 
 * @author emilysager
 */
package mw.server.gamelogic.logic;

import java.util.Collection;
import java.util.HashSet;

import mw.server.gamelogic.enums.Color;
import mw.server.gamelogic.enums.StructureType;
import mw.server.gamelogic.enums.UnitType;
import mw.server.gamelogic.enums.VillageType;
import mw.server.gamelogic.graph.GraphNode;
import mw.server.gamelogic.state.Tile;
import mw.server.gamelogic.state.Unit;


public final class TileGraphLogic {

	public static boolean isReachableNode(GraphNode pStart, GraphNode pCrt)
	{
		Tile crtTile = pCrt.getTile(); 
		Tile startTile = pStart.getTile();
		Collection<Tile> pCrtNeighbors = getNeighbors(pCrt); 
		if (startTile.hasUnit())
		{
			Unit pUnit = startTile.getUnit();
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
		
			return pStart.equals(pCrt); 
		


	}
	public static boolean isPathOver(GraphNode pStart, GraphNode pCrt)
	{
		Tile destinationTile = pCrt.getTile(); 
		Tile startTile = pStart.getTile();
		
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
	private static Collection<Tile> getNeighbors(GraphNode pNode) {
		Collection <GraphNode> tNeighbors = pNode.getAdjacentNodes(); 
		Collection <Tile> rNeighbors = new HashSet<Tile>();
		for (GraphNode lNode : tNeighbors)
		{
			rNeighbors.add(lNode.getTile()); 
		}
		return rNeighbors; 


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
			if (lUnit != null){
				if(lUnit.getUnitType().ordinal() >= pUnitType.ordinal()) {
					return false;
				}
			}
		}
		if (isWatchtowerGuardingTile(destinationTile, destinationNeighbors))
		{
			return false; 
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
	public static boolean isVillageBoundary(GraphNode startNode, GraphNode pNode )
	{
		Collection<GraphNode> pAdjNodes = pNode.getAdjacentNodes(); 
		boolean areAllNeighborsSameColor = true; 
		for (GraphNode lGraphNode : pAdjNodes)
		{
			if (!(lGraphNode.getTile().getColor() == startNode.getTile().getColor()))
			{
				areAllNeighborsSameColor = false; 
			}
		}
		return areAllNeighborsSameColor; 
	
	}
	public static boolean tilesAreSameColor(GraphNode startNode, GraphNode pNode)
	{
		if (startNode.getTile().getColor() == pNode.getTile().getColor())
		{
			return true; 
		}
		return false; 
	}
}


