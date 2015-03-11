package test.mw.server.gamelogic;

/**
 * @author Abhishek Gupta
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
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
	
	public static Game testMapCreate() throws TooManyPlayersException, IOException{
		
		//check here if the game has already been created in which case, load it from there 
		//else create it 
		File file = new File("savegame.txt");
		FileReader fileReader = new FileReader(file);
		BufferedReader br = new BufferedReader(fileReader);
		String inputString = br.readLine();
		br.close();
		if (inputString==null) {
			System.out.println("Game being created now");
			HashSet<Player> lPlayers = new HashSet<Player>();
			lPlayers.add(new Player());
			lPlayers.add(new Player());
			aTestGame = new Game(lPlayers, 12, 12);
			System.out.println("saving this game that got created");
			SaveGame.SaveMyGame(aTestGame);
		}
		else {
			System.out.println("saved game being loaded because one was already created");
			aTestGame = SaveGame.returnSavedGame();
		}
		return aTestGame;
	}
	
}
