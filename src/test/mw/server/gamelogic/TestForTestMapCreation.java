package test.mw.server.gamelogic;

/**
 * @author Abhishek Gupta
 */

import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import mw.server.gamelogic.exceptions.TooManyPlayersException;
import mw.server.gamelogic.model.Game;

public class TestForTestMapCreation {
	public static void main(String[] args) throws TooManyPlayersException, IOException{
		
		//System.out.println("Now testing whether test map can be successfully created");
		
		//Gson gson = new Gson();
		
		Game testGame = TestMap.testMapCreate();
		
		//System.out.println("first game ");
		
		//String tiles1 = gson.toJson(testGame.getGameTiles());
		
		Game testGame1 = TestMap.testMapCreate();
		
		//System.out.println("second game " + testGame1.toString());
		
		//String tiles2 = gson.toJson(testGame1.getGameTiles());
		
		//System.out.println(tiles1.equals(tiles2));
		
	}
	
	
}
