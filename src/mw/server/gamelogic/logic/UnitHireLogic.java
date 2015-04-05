package mw.server.gamelogic.logic;


import java.util.Collection;
import java.util.HashSet;

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
	
}
