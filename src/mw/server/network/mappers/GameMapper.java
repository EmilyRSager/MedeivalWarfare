/**
 * @author Charlie Bloomfield
 * Feb 25, 2015
 */

package mw.server.network.mappers;

import java.util.HashMap;
import java.util.Set;
import java.util.UUID;

import mw.server.gamelogic.state.Game;
import mw.server.network.lobby.GameID;


/**
 * @singleton
 * Maps AccountIDs to the games they're currently playing in. This may not be a good solution.
 */
public class GameMapper {
	private static GameMapper aGameMapper;
	private HashMap<UUID, GameID> aGameMap;
	
	/**
	 * Constructor
	 */
	private GameMapper() {
		aGameMap = new HashMap<UUID, GameID>();
	}
	
	/**
	 * Singleton implementation
	 * @return static GameIDMangager instance
	 */
	public static GameMapper getInstance(){
		if(aGameMapper == null){
			aGameMapper = new GameMapper();
		}
		
		return aGameMapper;
	}
	
	/**
	 * Puts a mapping between each AccountID in pAccountIDs and pGameID
	 * @param pAccountIDs
	 * @param pGameID
	 */
	public void putGameID(Set<UUID> pAccountIDs, GameID pGameID){
		for(UUID pAccountID : pAccountIDs){
			aGameMap.put(pAccountID, pGameID);
		}
	}
	
	/**
	 * Puts a mapping between pAccountID and pGameID in a HashMap
	 * @param pAccountID
	 * @param pGameID
	 */
	public void putGameID(UUID pAccountID, GameID pGameID){
		aGameMap.put(pAccountID, pGameID);
	}
	
	/**
	 * @param pAccountID
	 * @return GameID the pAccountID is currently playing
	 */
	public GameID getGameID(UUID pAccountID){
		return aGameMap.get(pAccountID);
	}
	
	/**
	 * @param pAccountID
	 * @return returns the Game instance wrapped by the GameID associated with pAccountID
	 */
	public Game getGame(UUID pAccountID){
		return aGameMap.get(pAccountID).getGame();
	}
	
	/**
	 * 
	 * @param pAccountID
	 */
	public void removeGameID(UUID pAccountID){
		aGameMap.remove(pAccountID);
	}
}
