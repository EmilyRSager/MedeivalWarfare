package test.mw.server.gamelogic;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

import mw.server.gamelogic.Coordinates;
import mw.server.gamelogic.Game;
import mw.server.gamelogic.Player;
import mw.server.gamelogic.Tile;
import mw.server.gamelogic.TooManyPlayersException;
import mw.server.gamelogic.Unit;
import mw.server.gamelogic.Village;

import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class TestPathFinder {

	@Test
	public void test() {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		
		Player p1 = new Player(), p2 = new Player();
		Collection<Player> players = new ArrayList<Player>(2);
		players.add(p1);
		players.add(p2);
		
		Game lGame = null;
		try {
			lGame = new Game(players);
		} catch (TooManyPlayersException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Tile lTile = lGame.getTile(new Coordinates(3, 4));
		Village lVillage = lGame.getVillage(lTile);
		Set<Tile> lVillageTiles = lVillage.getTiles();
		
		System.out.println(gson.toJson(lVillageTiles));	
	}
}
