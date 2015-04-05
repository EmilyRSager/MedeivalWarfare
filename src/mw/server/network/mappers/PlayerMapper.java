/**
 * @author Charlie Bloomfield
 * Feb 20, 2015
 */
package mw.server.network.mappers;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import mw.server.gamelogic.state.Player;


/**
 * Provides a Globally accessible mapping between AccountIDs and Player classes.
 * Different CommandHandlers will use this class to find Account's that need to receive messages,
 * based on their IDs.
 */
public class PlayerMapper {
	private static PlayerMapper aPlayerManagerInstance;
	private HashMap<UUID, Player> aPlayerMap;
	
	//TODO for now, maintain a mapping from Players to Accounts for simplicity. Seems super inelegant, not sure if this should be changed
	private HashMap<Player, UUID> aAccountMap;
	
	private PlayerMapper(){
		aPlayerMap = new HashMap<UUID, Player>();
		aAccountMap = new HashMap<Player, UUID>();
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
	public void putPlayer(UUID pAccountID, Player pPlayer){
		aPlayerMap.put(pAccountID, pPlayer);
		
		/**
		 * @fixme 
		 * Override the equals/hashcode method in the Player class.
		 */
		aAccountMap.put(pPlayer, pAccountID);
	}
	
	/**
	 * @param the AccountID that of the Player to be returned
	 * @return the Player that corresponds to pAccountID
	 */
	public Player getPlayer(UUID pAccountID){
		return aPlayerMap.get(pAccountID);
	}
	
	/**
	 * @param pAccountIDs
	 * @return set of Players
	 */
	public Set<Player> getPlayerSet(Set<UUID> pAccountIDs){
		Set<Player> lPlayerSet = new HashSet<Player>();
		for(UUID lAccountID : pAccountIDs){
			lPlayerSet.add(getPlayer(lAccountID));
		}
		
		return lPlayerSet;
	}
	
	/**
	 * @param pAccountID
	 * @return true if there exists a Player with pAccountID
	 */
	public boolean contains(UUID pAccountID){
		return aPlayerMap.containsKey(pAccountID);
	}
	
	/**
	 * @param pAccountIDs
	 * @return true if there exists a Player for every UUID in pAccountIDs
	 */
	public boolean containsAll(Set<UUID> pAccountIDs){
		for(UUID lAccountID : pAccountIDs){
			if(!aPlayerMap.containsKey(lAccountID)){
				return false;
			}
		}
		
		return true;
	}
	
	/**
	 * Removes the client specified by AccountID from the Player map
	 * @param pAccountID
	 * @return none
	 */
	public void removePlayer(int pAccountID){
		Player lPlayer = aPlayerMap.get(pAccountID);
		aPlayerMap.remove(pAccountID);
		aAccountMap.remove(lPlayer);
	}
	
	/**
	 * @param pPlayer
	 * @return Account associated with pPlayer
	 */
	public UUID getAccount(Player pPlayer){
		return aAccountMap.get(pPlayer);
	}
}
