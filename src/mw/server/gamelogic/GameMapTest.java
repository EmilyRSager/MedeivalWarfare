package mw.server.gamelogic;

import static org.junit.Assert.*;

import org.junit.Test;

public class GameMapTest {

	@Test
	public void GameMapConstructorTest() 
	{
		GameMap myMap = new GameMap(31, 11);
		myMap.partition();
		
		
	}

}
