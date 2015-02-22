package mw.server.gamelogic;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Set;

import org.junit.Test;

public class GameMapTest {

	@Test
	public void GameMapConstructorTest() 
	{
		GameMap myMap = new GameMap(4, 8);
		//myMap.partition();
		
				
				 int x = 2; 
				 int y = 4;
						
				Tile myTile2 = (Tile) myMap.getHexagon(x, y);
				
				ArrayList<HexagonalCoordinates> yoyoyo = myTile2.getNeighbors(); 
				
				System.out.println("Neighbors of " + x + ", " + y);
				for (HexagonalCoordinates lHexagon : yoyoyo )	
				{
					System.out.print("(" + lHexagon.getCoordinates()[0] + " , " + lHexagon.getCoordinates()[1] + ") ");
				}
		
				
		
		
	}

}
