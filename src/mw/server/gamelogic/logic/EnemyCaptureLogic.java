package mw.server.gamelogic.logic;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import mw.client.model.GameMap;
import mw.server.gamelogic.enums.StructureType;
import mw.server.gamelogic.graph.Graph;
import mw.server.gamelogic.state.Game;
import mw.server.gamelogic.state.Tile;
import mw.server.gamelogic.state.Village;

public class EnemyCaptureLogic {


	public static void CaptureTile(Village invadingVillage, Village invadedVillage,  Tile invadedTile, Game pGame)
	{

		//Case for invading a non-village capital
		if (invadedTile.getStructureType() != StructureType.VILLAGE_CAPITAL);
		{
			invadedVillage.removeTile(invadedTile);
			invadingVillage.addTile(invadedTile);
			Tile invadingCapital = invadingVillage.getCapital(); 
			Collection<Village> toFuse = new ArrayList<Village>(); 
			boolean needToFuse = false; 

			for(Tile lTile : pGame.getNeighbors(invadedTile))
			{
				if(lTile.getColor() == invadingVillage.getColor() ) //check the neighbors to see which same-color villages are fuse candidates
				{
					if (!pGame.getVillage(lTile).equals(invadingVillage)) // make sure we aren't just looking at the invading village over and over
						toFuse.add(pGame.getVillage(lTile)); 
					needToFuse = true; 
				}
			}
			if(needToFuse)
			{
				pGame.fuseVillages(toFuse, invadingCapital);
			}

		}

	}

	public static Collection<Tile>  getCurrentCapitals(Game pGame)
	{
		Collection <Village> lInitialVillages = pGame.getVillages(); 
		Collection <Tile> lCurrentCapitals = new HashSet<Tile>(); 
		for (Village lVillage: lInitialVillages)
		{
			lCurrentCapitals.add(lVillage.getCapital()); 
		}
		return lCurrentCapitals;
	}

}  
