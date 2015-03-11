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

import mw.client.files.ProjectFolder;
import mw.server.gamelogic.Game;

public class SaveGame {
	
	
	public static void SaveMyGame(Game pGame) throws IOException{
		//System.out.println("game being serialized");
		
		//System.out.println("writing to the savegame file");
		//PrintWriter out = new PrintWriter(ProjectFolder.getPath()+"savegame.txt");
		FileOutputStream out = new FileOutputStream(ProjectFolder.getPath()+"savegame.txt");
		//System.out.println("got here 1");
		ObjectOutputStream oos = new ObjectOutputStream(out);
		//System.out.println("got here 2");
		oos.writeObject(pGame);
		//System.out.println("got here 3");
		out.close();
	}
	
	public static Game returnSavedGame() throws IOException, ClassNotFoundException{
		//later can be expanded to take in a player ID or something to return the game that is associated with them
//		File file = new File(ProjectFolder.getPath()+"savegame.txt");
//		FileReader fReader = new FileReader(file);
//		BufferedReader br = new BufferedReader(fReader);
//		String input = br.readLine();
//		fReader.close();
		
		FileInputStream fis = new FileInputStream(ProjectFolder.getPath()+"savegame.txt");
		ObjectInputStream ois = new ObjectInputStream(fis);
		Game lGame = (Game)ois.readObject();
		
		return lGame;
	}
	
	
	
}
