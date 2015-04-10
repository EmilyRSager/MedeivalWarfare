package mw.server.gamelogic.logic;

import java.util.Collection;
import java.util.HashSet;

import mw.server.gamelogic.enums.ActionType;
import mw.server.gamelogic.enums.StructureType;
import mw.server.gamelogic.enums.VillageType;
import mw.server.gamelogic.state.Game;
import mw.server.gamelogic.state.GameMap;
import mw.server.gamelogic.state.Player;
import mw.server.gamelogic.state.Tile;
import mw.server.gamelogic.state.Unit;
import mw.server.gamelogic.state.Village;

public class EnemyCaptureLogic {


	public static void TakeoverNeutral(Village invadingVillage, Tile invadedTile, Game pGame, Player aCurrentPlayer)
	{
		invadedTile.setColor(invadingVillage.getColor());
		invadingVillage.addTile(invadedTile);
		fuse(invadingVillage, null, invadedTile, pGame, aCurrentPlayer);
	}
	public static void CaptureTile(Village invadingVillage, Village invadedVillage,  Tile invadedTile, Game pGame, Player aCurrentPlayer)
	{
		//Case for invading a non-village capital
		if (invadedTile.getStructureType() != StructureType.VILLAGE_CAPITAL);
		{
			System.out.println("[Game] The tile this Unit is trying to take over is not the village capital.");

			invadedVillage.removeTile(invadedTile);
			invadedTile.setColor(invadingVillage.getColor());
			invadingVillage.addTile(invadedTile);
			fuse(invadingVillage, invadedVillage, invadedTile, pGame, aCurrentPlayer);
		}
		if(invadedTile.getStructureType() == StructureType.VILLAGE_CAPITAL)
		{
			invadeCapital(invadingVillage, invadedVillage, invadedTile, pGame, aCurrentPlayer);
		}
	}

	private static void fuse(Village invadingVillage, Village invadedVillage,  Tile invadedTile, Game pGame, Player aCurrentPlayer) {
		for(Tile neighbor : pGame.getNeighbors(invadedTile)){
			if(tileNeighborsCauseFuse(pGame, invadedTile, neighbor)){
				fuseVillages(invadingVillage, pGame.getVillage(neighbor), aCurrentPlayer, pGame.getGameMap());
			}
		}
	}
	
	private static boolean tileNeighborsCauseFuse(Game game, Tile t1, Tile t2){
		return t1.getColor() == t2.getColor() && game.getVillage(t1) != game.getVillage(t2);
	}
	
	private static void fuseVillages(Village v1, Village v2, Player fusingPlayer, GameMap gameMap){
		Village strongerVillage = getStrongerVillage(v1, v2);
		Village weakerVillage = (v1 != strongerVillage) ? v1 : v2;
		
		strongerVillage.addTiles(weakerVillage.getTiles());
		
		weakerVillage.getCapital().setVillageType(VillageType.NO_VILLAGE);
		weakerVillage.getCapital().setStructureType(StructureType.NO_STRUCT);
		weakerVillage.getCapital().notifyChanged();
		
		strongerVillage.addOrSubtractGold(weakerVillage.getGold());
		strongerVillage.addOrSubtractWood(weakerVillage.getWood());
		strongerVillage.getCapital().notifyChanged();
		
		fusingPlayer.removeVillage(weakerVillage);
		gameMap.removeVillage(weakerVillage);
	}
	
	private static Village getStrongerVillage(Village v1, Village v2){
		return v1.getVillageType().ordinal() > v2.getVillageType().ordinal() ? v1 : v2;
	}
	
	private static void invadeCapital(Village invadingVillage, Village invadedVillage, Tile invadedTile, Game pGame, Player aCurrentPlayer) 
	{

		int lGold = invadedVillage.getGold();
		int lWood = invadedVillage.getWood();
		invadingVillage.addOrSubtractGold(lGold);
		invadingVillage.addOrSubtractWood(lWood);
		invadedVillage.addOrSubtractGold(-lGold);
		invadedVillage.addOrSubtractWood(-lWood);
		invadedVillage.setRandomCapital();
		fuse(invadingVillage, invadedVillage, invadedTile, pGame, aCurrentPlayer);
	}
	
	public static void move(Unit pUnit, Tile pDestinationTile, Village pInvadingVillage)
	{
		StructureType pStructureType = pDestinationTile.getStructureType();
		switch (pStructureType)
		{
		case TREE:
			pInvadingVillage.addOrSubtractWood(1);
			pDestinationTile.setStructureType(StructureType.NO_STRUCT);
		default:
			pDestinationTile.setStructureType(StructureType.NO_STRUCT);
		}
		
		pDestinationTile.setUnit(pUnit);
		pUnit.setActionType(ActionType.MOVED);
		pDestinationTile.notifyObservers();
		
	}
}
