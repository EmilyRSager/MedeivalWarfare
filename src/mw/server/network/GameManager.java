/**
 * @author Charlie Bloomfield
 * Feb 25, 2015
 */

package mw.server.network;

import java.util.HashMap;

import mw.server.gamelogic.Game;

/**
 * @singleton
 * Maps ClientIDs to the games they're currently playing in. This may not be a good solution.
 */
public class GameManager {
	private static GameManager aGameManager;
	private HashMap<Integer, Game> aGameMap;
	
	/**
	 * Constructor
	 */
	private GameManager() {
		aGameMap = new HashMap<Integer, Game>();
	}
	
	/**
	 * Singleton implementation
	 * @return static GameMangager instance
	 */
	public static GameManager getInstance(){
		if(aGameManager == null){
			aGameManager = new GameManager();
		}
		
		return aGameManager;
	}
	
	/**
	 * puts a mapping between pClientID and pGame in a HashMap
	 * @param pClientID
	 * @param pGame
	 */
	public void putGame(Integer pClientID, Game pGame){
		aGameMap.put(pClientID, pGame);
	}
	
	/**
	 * @param pClientID
	 * @return Game the pClientID is currently playing
	 */
	public Game getGame(Integer pClientID){
		return aGameMap.get(pClientID);
	}
}