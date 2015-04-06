package test.mw.server.gamelogic;

/**
 * @author Abhishek Gupta
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;

import com.google.gson.Gson;

import mw.filesystem.ProjectFolder;
import mw.server.gamelogic.state.Game;
import mw.server.gamelogic.state.GameID;

public class SaveGame {
	
	
	public static void SaveMyGame(GameID pGame) throws IOException{
		//System.out.println("game being serialized");
		
		//System.out.println("writing to the savegame file");
		//PrintWriter out = new PrintWriter(ProjectFolder.getPath()+"savegame.txt");
		FileOutputStream out = new FileOutputStream(ProjectFolder.getPath()+"GameData"+pGame.getaName());
		//System.out.println("got here 1");
		ObjectOutputStream oos = new ObjectOutputStream(out);
		//System.out.println("got here 2");
		oos.writeObject(pGame);
		//System.out.println("got here 3");
		out.close();
	}
	
	public static GameID returnSavedGame() throws IOException, ClassNotFoundException{
		//later can be expanded to take in a player ID or something to return the game that is associated with them
//		File file = new File(ProjectFolder.getPath()+"savegame.txt");
//		FileReader fReader = new FileReader(file);
//		BufferedReader br = new BufferedReader(fReader);
//		String input = br.readLine();
//		fReader.close();
		
		FileInputStream fis = new FileInputStream(ProjectFolder.getPath()+"savegame.txt");
		ObjectInputStream ois = new ObjectInputStream(fis);
		GameID lGame = (GameID)ois.readObject();
		
		return lGame;
	}
	
	
	
}
