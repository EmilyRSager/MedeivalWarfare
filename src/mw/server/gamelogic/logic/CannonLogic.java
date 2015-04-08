package mw.server.gamelogic.logic;

import java.util.Collection;




import java.util.HashSet;
import java.util.Set;

import mw.server.gamelogic.enums.ActionType;
import mw.server.gamelogic.enums.Color;
import mw.server.gamelogic.state.Game;
import mw.server.gamelogic.state.Tile;

public class CannonLogic 
{

	public static Set<Tile> getReachableTiles(Tile cannonTile, Game pGame)
	{
		
		Set<Tile> rReachableTiles = new HashSet<Tile>();
		if (cannonTile.getUnit().getActionType()!=ActionType.READY)
		{
			return rReachableTiles;
		}
		
		for (Tile lTile : pGame.getNeighbors(cannonTile))
		{
			if (isReachableNode(lTile, cannonTile, pGame.getNeighbors(lTile)))
			{
				rReachableTiles.add(cannonTile);
			}
		}
		return rReachableTiles;
	}
	
	public static boolean isReachableNode(Tile crtTile, Tile startTile, Collection<Tile> pCrtNeighbors)
	{
		if (!pCrtNeighbors.contains(startTile))
		{
			return false; 
		}
		if (crtTile.getColor() != startTile.getColor() )
		{
			return false; 
		}
		switch (crtTile.getStructureType())
		{
		case TOMBSTONE:
			return false; 
		case TREE: 
			return false;
		case WATCHTOWER: 
			return false;
		case VILLAGE_CAPITAL: 
			return false;
		default: 
			break;
		}
		return true; 
	}
	
	public static Set<Tile> getFirableTiles(Tile cannonTile, Game pGame)
	{
		Set<Tile> rFirableTiles = new HashSet<Tile>();
		
		if (cannonTile.getUnit().getActionType() != ActionType.READY)
		{
			return rFirableTiles;
		}
		
		Collection<Tile> cannonNeighbors = pGame.getNeighbors(cannonTile);
		for (Tile lTile : cannonNeighbors)
		{
			if (lTile.getColor() != cannonTile.getColor() && lTile.getColor()!=Color.NEUTRAL)
			{
				rFirableTiles.add(lTile);
				
			}
			Collection<Tile> NeighborsOfCannonNeighbors = pGame.getNeighbors(lTile);
			for (Tile lTile2 : NeighborsOfCannonNeighbors)
			{
				if (lTile2.getColor() != cannonTile.getColor() && lTile2.getColor()!=Color.NEUTRAL)
				{
					rFirableTiles.add(lTile2);
				}
			}
	
		}
			
		return rFirableTiles; 
	}
	
	
	
}
