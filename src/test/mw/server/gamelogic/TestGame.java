package test.mw.server.gamelogic;

import java.util.ArrayList;
import java.util.Collection;

import com.google.gson.Gson;

import mw.server.gamelogic.Game;
import mw.server.gamelogic.Player;
import mw.server.gamelogic.Tile;
import mw.server.gamelogic.TooManyPlayersException;
import mw.server.network.translators.SharedTileTranslator;
import mw.shared.SharedTile;

public class TestGame {
	public static void main(String[] args) {
		
		Collection<Player> myPlayers = new ArrayList<Player> ();  
		Player p1 = new Player(); 
		
		Player p2 = new Player(); 
		myPlayers.add(p1);
		myPlayers.add(p2);
		
		try {
			Game g = new Game(myPlayers, 0);
			testConvertGameMap(g.getGameTiles(), g);
			//g.printTiles();
		} catch (TooManyPlayersException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void testConvertGameMap(Tile[][] pGameMap, Game pGame){
		Gson gson = new Gson();
		SharedTile[][] s = SharedTileTranslator.translateMap(pGameMap, pGame);
		for(int row = 0; row < s.length; row++){
			for(int col = 0; col < s[0].length; col++){
				System.out.println(gson.toJson(s[row][col]));
			}
		}
	}
}
