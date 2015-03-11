package test.mw.server.gamelogic;

import com.google.gson.Gson;

import mw.server.gamelogic.Game;

public class SaveGame {
	
	private static String aTheSavedGame;
	
	public static void SaveMyGame(Game pGame){
		aTheSavedGame = new Gson().toJson(pGame);
	}
	
	public static String returnSavedGame(){
		//later can be expanded to take in a player ID or something to return the game that is associated with them
		return aTheSavedGame;
	}
	
	
	
}
