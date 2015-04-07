package test.mw.server.gamelogic;

/**
 * @author Abhishek Gupta
 */
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import mw.filesystem.ProjectFolder;
import mw.server.gamelogic.state.GameID;

public class SaveGame {
	private static String GAME_DATA_PATH = "data/savedgames/";
	public static void SaveMyGame(GameID pGame) throws IOException{
		//System.out.println("game being serialized");
		
		//System.out.println("writing to the savegame file");
		//PrintWriter out = new PrintWriter(ProjectFolder.getPath()+"savegame.txt");
		FileOutputStream out = new FileOutputStream(ProjectFolder.getPath() + GAME_DATA_PATH + pGame.getaName());
		//System.out.println("got here 1");
		ObjectOutputStream oos = new ObjectOutputStream(out);
		//System.out.println("got here 2");
		oos.writeObject(pGame);
		//System.out.println("got here 3");
		out.close();
	}
	
	public static GameID returnSavedGame(String pGameName) throws IOException, ClassNotFoundException{
		//later can be expanded to take in a player ID or something to return the game that is associated with them
//		File file = new File(ProjectFolder.getPath()+"savegame.txt");
//		FileReader fReader = new FileReader(file);
//		BufferedReader br = new BufferedReader(fReader);
//		String input = br.readLine();
//		fReader.close();
		
		FileInputStream fis = new FileInputStream(ProjectFolder.getPath() + GAME_DATA_PATH + pGameName);
		ObjectInputStream ois = new ObjectInputStream(fis);
		GameID lGame = (GameID)ois.readObject();
		
		ois.close();
		return lGame;
	}
	
	
	
}
