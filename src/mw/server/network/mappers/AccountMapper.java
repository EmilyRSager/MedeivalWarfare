/**
 * @author Charlie Bloomfield
 * Feb 23, 2015
 */

package mw.server.network.mappers;

import java.util.HashMap;
import java.util.UUID;

import mw.server.admin.Account;

/**
 * Provides maintaining a set of Accounts and mapping ClientIDs to those accounts.
 */
public class AccountMapper {
	private HashMap<Integer, UUID> aUUIDMap;
	private HashMap<UUID, Integer> aClientIDMap;
	private static AccountMapper aAccountManager;
	
	/**
	 * Constructor
	 */
	private AccountMapper(){
		aUUIDMap = new HashMap<Integer, UUID>();
		aClientIDMap = new HashMap<UUID, Integer>();
	}
	
	public static AccountMapper getInstance(){
		if(aAccountManager == null){
			aAccountManager = new AccountMapper();
		}
		
		return aAccountManager;
	}
	
	/**
	 * Adds pAccount to the Map of accounts.
	 * @param pClientID
	 * @param pAccount
	 */
	public void put(Integer pClientID, UUID pUUID){
		aUUIDMap.put(pClientID, pUUID);
		aClientIDMap.put(pUUID, pClientID);
	}
	
	/**
	 * @param pClientID
	 * @return Accoutn associated with pClientID if it exists
	 */
	public UUID getAccountUUID(Integer pClientID){
		return aUUIDMap.get(pClientID);
	}
	
	/**
	 * @param pUUID
	 * @return
	 */
	public Integer getClientID(UUID pUUID){
		return aClientIDMap.get(pUUID);
	}
	
	public void remove(UUID pUUID){
		Integer lTargetClientID = aClientIDMap.get(pUUID);
		aUUIDMap.remove(lTargetClientID);
		aClientIDMap.remove(pUUID);
	}
	
	public void remove(Integer pClientID){
		UUID lTargetUUID = aUUIDMap.get(pClientID);
		aUUIDMap.remove(pClientID);
		aClientIDMap.remove(lTargetUUID);
	}
}
