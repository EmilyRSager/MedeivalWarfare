/**
 * @author Charlie Bloomfield
 * Feb 20, 2015
 */
package mw.server.network.mappers;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import mw.server.gamelogic.Player;

/**
 * Provides a Globally accessible mapping between ClientIDs and Player classes.
 * Different CommandHandlers will use this class to find Client's that need to receive messages,
 * based on their IDs.
 */
public class PlayerMapper {
	private static PlayerMapper aPlayerManagerInstance;
	private HashMap<Integer, Player> aPlayerMap;
	
	private PlayerMapper(){
		aPlayerMap = new HashMap<Integer, Player>();
	}
	
	/**
	 * Singleton getInstance method
	 * @param none
	 * @return the static PlayerManager instance
	 */
	public static PlayerMapper getInstance(){
		if(aPlayerManagerInstance == null){
			aPlayerManagerInstance = new PlayerMapper();
		}
		
		return aPlayerManagerInstance;
	}
	
	/**
	 * Maps the ID of pPlayer to that client.
	 * @param The Player to be added to the map
	 * @return none
	 */
	public void putPlayer(Integer pClientID, Player pPlayer){
		aPlayerMap.put(pClientID, pPlayer);
	}
	
	/**
	 * @param the ClientID that of the Player to be returned
	 * @return the Player that corresponds to pClientID
	 */
	public Player getPlayer(Integer pClientID){
		return aPlayerMap.get(pClientID);
	}
	
	/**
	 * @param pClientIDs
	 * @return set of Players
	 */
	public Set<Player> getPlayerSet(Set<Integer> pClientIDs){
		Set<Player> lPlayerSet = new HashSet<Player>();
		for(Integer lClientID : pClientIDs){
			lPlayerSet.add(getPlayer(lClientID));
		}
		
		return lPlayerSet;
	}
	
	/**
	 * @param pClientID
	 * @return true if there exists a Player with pClientID
	 */
	public boolean contains(Integer pClientID){
		return aPlayerMap.containsKey(pClientID);
	}
	
	/**
	 * @param pClientIDs
	 * @return true if there exists a Player for every Integer in pClientIDs
	 */
	public boolean containsAll(Set<Integer> pClientIDs){
		for(Integer lClientID : pClientIDs){
			if(!aPlayerMap.containsKey(lClientID)){
				return false;
			}
		}
		
		return true;
	}
	
	/**
	 * Removes the client specified by ClientID from the
	 * @param pClientID
	 * @return none
	 */
	public void removePlayer(int pClientID){
		aPlayerManagerInstance.removePlayer(pClientID);
	}
}
