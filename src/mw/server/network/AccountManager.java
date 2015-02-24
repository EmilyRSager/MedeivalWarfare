/**
 * @author Charlie Bloomfield
 * Feb 23, 2015
 */

package mw.server.network;

import java.util.HashMap;

/**
 * Provides maintaining a set of Accounts and mapping ClientIDs to those accounts.
 */
public class AccountManager {
	private HashMap<Integer, Account> aAccountMap;
	private static AccountManager aAccountManager;
	
	/**
	 * Constructor
	 */
	public AccountManager(){
		aAccountMap = new HashMap<Integer, Account>();
	}
	
	/**
	 * Adds pAccount to the Map of accounts.
	 * @param pClientID
	 * @param pAccount
	 */
	public void putAccounts(Integer pClientID, Account pAccount){
		aAccountMap.put(pClientID, pAccount);
	}
	
	/**
	 * @param pClientID
	 * @return Accoutn associated with pClientID if it exists
	 */
	public Account getAccount(Integer pClientID){
		return aAccountMap.get(pClientID);
	}
}
