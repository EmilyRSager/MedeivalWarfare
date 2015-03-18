package mw.server.gamelogic.logic;

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
			
			//Collection<Tile> currentCapitals = getCurrentCapitals(pGame);
			//Collection<Village> startConfiguration = pGame.getVillages(); 
			//recalculateVillages(pGame);
			
			for(Tile lTile : pGame.getNeighbors()){
				if(lTile.getColor() == invadingVillage.getColor() && !pGame.getVillage(lTile).equals(pGame.getVillage(invadedTile))){
					fuseVillages(invadingVillage, invadedVillage);
				}
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

	public static void recalculateVillages(Game pGame)
	{
		pGame.recalculateVillages(); 
	}
}  
