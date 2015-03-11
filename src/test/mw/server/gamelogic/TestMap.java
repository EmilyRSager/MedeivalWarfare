package test.mw.server.gamelogic;

/**
 * @author Abhishek Gupta
 */

import java.util.Collection;
import java.util.HashSet;

import mw.server.gamelogic.Game;
import mw.server.gamelogic.Player;
import mw.server.gamelogic.TooManyPlayersException;
import mw.server.gamelogic.Village;

public class TestMap {
	private static Game aTestGame; 
	
	//create a map by running the game once, capture it, same for the game itself and make it available statically
	//create a save game class and serialize and assign the game to it and then have something to load the game and 
	// bring it back into 
	
	public static Game testMapCreate() throws TooManyPlayersException{
		
		//check here if the game has already been created in which case, load it from there 
		//else create it 
		
		
		
		if (aTestGame==null) {
			HashSet<Player> lPlayers = new HashSet<Player>();
			HashSet<Village> lVillages1 = new HashSet<Village>();
			lPlayers.add(new Player());
			lPlayers.add(new Player());
			aTestGame = new Game(lPlayers, 12, 12);
		}
		else {
			
		}
		return aTestGame;
	}
	
}
