/**
 * @author Charlie Bloomfield
 * Feb 25, 2015
 */

package mw.server.network.mappers;

import java.util.HashMap;
import java.util.Set;
import java.util.UUID;

import mw.server.gamelogic.state.Game;


/**
 * @singleton
 * Maps AccountIDs to the games they're currently playing in. This may not be a good solution.
 */
public class GameMapper {
	private static GameMapper aGameManager;
	private HashMap<UUID, Game> aGameMap;
	
	/**
	 * Constructor
	 */
	private GameMapper() {
		aGameMap = new HashMap<UUID, Game>();
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
	 * Puts a mapping between each AccountID in pAccountIDs and pGame
	 * @param pAccountIDs
	 * @param pGame
	 */
	public void putGame(Set<UUID> pAccountIDs, Game pGame){
		for(UUID pAccountID : pAccountIDs){
			aGameMap.put(pAccountID, pGame);
		}
	}
	
	/**
	 * Puts a mapping between pAccountID and pGame in a HashMap
	 * @param pAccountID
	 * @param pGame
	 */
	public void putGame(UUID pAccountID, Game pGame){
		aGameMap.put(pAccountID, pGame);
	}
	
	/**
	 * @param pAccountID
	 * @return Game the pAccountID is currently playing
	 */
	public Game getGame(UUID pAccountID){
		return aGameMap.get(pAccountID);
	}
}
