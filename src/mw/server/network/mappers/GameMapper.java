/**
 * @author Charlie Bloomfield
 * Feb 25, 2015
 */

package mw.server.network.mappers;

import java.util.HashMap;
import java.util.Set;

import mw.server.gamelogic.model.Game;

/**
 * @singleton
 * Maps ClientIDs to the games they're currently playing in. This may not be a good solution.
 */
public class GameMapper {
	private static GameMapper aGameManager;
	private HashMap<Integer, Game> aGameMap;
	
	/**
	 * Constructor
	 */
	private GameMapper() {
		aGameMap = new HashMap<Integer, Game>();
	}
	
	/**
	 * Singleton implementation
	 * @return static GameMangager instance
	 */
	public static GameMapper getInstance(){
		if(aGameManager == null){
			aGameManager = new GameMapper();
		}
		
		return aGameManager;
	}
	
	/**
	 * Puts a mapping between each ClientID in pClientIDs and pGame
	 * @param pClientIDs
	 * @param pGame
	 */
	public void putGame(Set<Integer> pClientIDs, Game pGame){
		for(Integer pClientID : pClientIDs){
			aGameMap.put(pClientID, pGame);
		}
	}
	
	/**
	 * Puts a mapping between pClientID and pGame in a HashMap
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
