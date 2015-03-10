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
		
		System.out.println(lTiles[0][0].toString());
		
		GameController.upgradeUnit(lGame, 0, 0, UnitType.INFANTRY);
		
		System.out.println(lTiles[0][0].toString());
		
		lTiles[0][1].setColor(lTiles[0][0].getColor());
		GameController.moveUnit(lGame, 0, 0, 1, 1);
		
		System.out.println(lTiles[0][0].toString());
	}

}
