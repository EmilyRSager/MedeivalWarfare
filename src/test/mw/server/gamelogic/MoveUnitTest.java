package test.mw.server.gamelogic;
import java.util.HashSet;
import java.util.Set;

import mw.server.gamelogic.*;
import mw.server.gamelogic.controllers.GameController;
import mw.server.gamelogic.enums.StructureType;
import mw.server.gamelogic.enums.UnitType;
import mw.server.gamelogic.state.Game;
import mw.server.gamelogic.state.Tile;
import static org.junit.Assert.*;

import org.junit.Test;


public class MoveUnitTest {

	@Test
	public void testMoveUnit() {
		Game lGame = new Game();
		Tile[] []  lTiles = lGame.getGameTiles();
		
		lTiles[3][8].setStructureType(StructureType.TREE);
		System.out.println(lTiles[1][7].toString());
		System.out.println(lTiles[3][8].toString());
		
		GameController.hireUnit(lGame, 1, 7, UnitType.PEASANT);
		GameController.moveUnit(lGame, 1, 7, 3, 8);
		
		System.out.println(lTiles[1][7].toString());
		System.out.println(lTiles[3][8].toString());
		
		System.out.println(lGame.getVillage(lTiles[3][8]).getWood());
		
		
		
	}
	
		
		
}


