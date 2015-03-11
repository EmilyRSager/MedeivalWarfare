package test.mw.server.gamelogic;

import java.io.IOException;

import com.google.gson.Gson;
import mw.server.gamelogic.Game;
import mw.server.gamelogic.TooManyPlayersException;

public class TestForTestMapCreation {
	public static void main(String[] args) throws TooManyPlayersException, IOException{
		
		System.out.println("Now testing whether test map can be successfully created");
		
		Game testGame = TestMap.testMapCreate();
		
		System.out.println("first game "+(new Gson().toJson(testGame)).toString());
		
		Game testGame1 = TestMap.testMapCreate();
		
		System.out.println("second game "+(new Gson().toJson(testGame1)).toString());
		
	}
	
	
}
