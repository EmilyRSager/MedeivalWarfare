package test.mw.server.gamelogic;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import com.google.gson.Gson;

import mw.server.gamelogic.Game;

public class SaveGame {
	
	
	public static void SaveMyGame(Game pGame) throws FileNotFoundException{
		String theGame = new Gson().toJson(pGame);
		PrintWriter out = new PrintWriter("savegame.txt");
		out.println(theGame);
		out.close();
	}
	
	public static Game returnSavedGame() throws IOException{
		//later can be expanded to take in a player ID or something to return the game that is associated with them
		File file = new File("savegame.txt");
		FileReader fReader = new FileReader(file);
		BufferedReader br = new BufferedReader(fReader);
		String input = br.readLine();
		fReader.close();
		return new Gson().fromJson(input, Game.class);
	}
	
	
	
}
