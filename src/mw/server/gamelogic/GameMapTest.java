package mw.server.gamelogic;

import static org.junit.Assert.*;

import java.util.ArrayList;



import org.junit.Test;

public class GameMapTest
{

	@Test
	public void test()
	{
		Player p1 = new Player (); 
		Player p2 = new Player(); 
		
		ArrayList<Player> pPlayers = new ArrayList<Player>(); 
		pPlayers.add(p1);
		pPlayers.add(p2);
		
		try
		{
			Game g = new Game(pPlayers,0);
		//g.printTiles();
		} catch (TooManyPlayersException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
