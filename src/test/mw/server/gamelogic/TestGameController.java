package test.mw.server.gamelogic;

import static org.junit.Assert.*;
import mw.server.gamelogic.*;

import org.junit.Test;

public class TestGameController {

	@Test
	public void setSetActionType() throws TooManyPlayersException{
		Game lGame = GameController.newGame(3);
		GameController.hireUnit(lGame, 0, 0, UnitType.PEASANT);
		
		
		Tile[][] lTiles = lGame.getGameTiles();
		
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
