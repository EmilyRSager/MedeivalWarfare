/**
 * @author Charlie Bloomfield
 * Feb 23, 2015
 */

package mw.server.admin;

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
	private AccountManager(){
		aAccountMap = new HashMap<Integer, Account>();
	}
	
	public static AccountManager getInstance(){
		if(aAccountManager == null){
			aAccountManager = new AccountManager();
		}
		
		return aAccountManager;
	}
	
	/**
	 * Adds pAccount to the Map of accounts.
	 * @param pClientID
	 * @param pAccount
	 */
	public void putAccount(Integer pClientID, Account pAccount){
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
