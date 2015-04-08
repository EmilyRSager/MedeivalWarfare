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

import mw.filesystem.ProjectFolder;
import mw.server.gamelogic.state.Game;
import mw.server.gamelogic.state.Player;
import mw.server.gamelogic.exceptions.TooManyPlayersException;
import mw.server.gamelogic.state.Village;

public class TestMap {
	private static Game aTestGame; 
	
	//create a map by running the game once, capture it, same for the game itself and make it available statically
	//create a save game class and serialize and assign the game to it and then have something to load the game and 
	// bring it back into 
	
	public static Game testMapCreate() throws mw.server.gamelogic.exceptions.TooManyPlayersException, IOException{
		
		//check here if the game has already been created in which case, load it from there 
		//else create it 
		
		//the file would be empty if the game has never been created, otherwise we load from the file
		
		
		
			try {
				FileReader fileReader = new FileReader(ProjectFolder.getPath()+"savegame.txt");
//				BufferedReader br = new BufferedReader(fileReader);
//				String inputString = br.readLine();
//				br.close();
				//System.out.println("saved game being loaded because one was already created");
				aTestGame = GameMarshaller.returnSavedGame();
			} catch (Exception e) {
					
						//System.out.println("Game being created now");
						HashSet<Player> lPlayers = new HashSet<Player>();
						lPlayers.add(new Player());
						lPlayers.add(new Player());
						aTestGame = new Game(lPlayers, "testgame");
						//System.out.println("saving this game that got created");
						GameMarshaller.saveGame(aTestGame);
						//System.out.println("after the savemygame");
				
				
			}
		
				
				
			
			
			
		return aTestGame;
	}
	
}
