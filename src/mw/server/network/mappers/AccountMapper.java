/**
 * @author Charlie Bloomfield
 * Feb 23, 2015
 */

package mw.server.network.mappers;

import java.util.HashMap;

import mw.server.admin.Account;

/**
 * Provides maintaining a set of Accounts and mapping ClientIDs to those accounts.
 */
public class AccountMapper {
	private HashMap<Integer, Account> aAccountMap;
	private static AccountMapper aAccountManager;
	
	/**
	 * Constructor
	 */
	private AccountMapper(){
		aAccountMap = new HashMap<Integer, Account>();
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
