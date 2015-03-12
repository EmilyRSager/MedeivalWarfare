package test.mw.server.gamelogic;

import static org.junit.Assert.*;
import mw.server.gamelogic.*;
import mw.server.gamelogic.controllers.GameController;
import mw.server.gamelogic.enums.Color;
import mw.server.gamelogic.enums.StructureType;
import mw.server.gamelogic.enums.UnitType;
import mw.server.gamelogic.enums.VillageType;
import mw.server.gamelogic.exceptions.TooManyPlayersException;
import mw.server.gamelogic.state.Game;
import mw.server.gamelogic.state.Tile;

import org.junit.Test;

public class TestGameController {

	@Test
	public void setSetActionType() throws TooManyPlayersException{
		Game lGame = GameController.newGame(3);
		
		Tile[][] lTiles = lGame.getGameTiles();
		
		while (!(lGame.getVillage(lTiles[0][0])).equals(lGame.getVillage(lTiles[1][1])))
		{
			lGame = GameController.newGame(3);
			lTiles = lGame.getGameTiles();

		}
	
		GameController.hireUnit(lGame, 0, 0, UnitType.PEASANT);
		
		//GameController.upgradeUnit(lGame, 0, 0, UnitType.INFANTRY);

		lTiles[0][1].setColor(Color.GREEN);
		lTiles[1][0].setColor(Color.GREEN);
		lTiles[1][1].setColor(Color.GREEN);
		lTiles[0][0].setColor(Color.GREEN);
		
		
		lTiles[1][0].setVillageType(VillageType.NO_VILLAGE);
		lTiles[0][1].setVillageType(VillageType.NO_VILLAGE);
		lTiles[0][0].setVillageType(VillageType.NO_VILLAGE);
		lTiles[1][1].setVillageType(VillageType.NO_VILLAGE);
		
		lTiles[1][0].setStructureType(StructureType.NO_STRUCT);
		lTiles[0][1].setStructureType(StructureType.NO_STRUCT);
		lTiles[0][0].setStructureType(StructureType.NO_STRUCT);
		lTiles[1][1].setStructureType(StructureType.TREE);
		
		
		System.out.println("Start Tile Initially:");
		System.out.println(lTiles[0][0].toString());
		
		System.out.println("Destination Tile initially: ");
		System.out.println(lTiles[1][1].toString());
		
		
		
		GameController.moveUnit(lGame, 0, 0, 1, 1);
		
		System.out.println(lTiles[0][0].toString());
		System.out.println(lTiles[1][1].toString());
		System.out.println(lGame.getVillage(lTiles[1][1]).getWood());
		
		
	}

}
