/**
 * @author Charlie Bloomfield
 * Feb 23, 2015
 */

package mw.server.admin;

import java.util.List;
import java.util.UUID;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import mw.client.model.Game;

/**
 * Represents a user account in the MedeivalWarfare game system. Keeps track of relevant information,
 * such as wins, losses, friends, etc.
 */
public class Account {
	private UUID aUUID;
	private String aUsername;
	private String aPassword;
	private AccountGameInfo aAccountGameInfo;
	
	/**
	 * Overloaded constructor
	 * @param pUUID
	 * @param pUsername
	 * @param pPassword
	 */
	public Account(UUID pUUID, String pUsername, String pPassword){
		this(pUUID, pUsername, pPassword, new AccountGameInfo());
	}
	
	/**
	 * Constructor
	 * @param pUsername
	 * @param pPassword
	 */
	public Account(UUID pUUID, String pUsername, String pPassword, AccountGameInfo pAccountGameInfo){
		aUUID = pUUID;
		aUsername = pUsername;
		aPassword = pPassword;
		aAccountGameInfo = pAccountGameInfo;
	}
	
	/**
	 * @param pUsername
	 * @param pPassword
	 * @return true if this account has credentials equal to pUsername and pPassword
	 */
	public boolean hasCredentials(String pPassword){
		return aPassword.equals(pPassword);
	}
	
	/**
	 * 
	 * @return
	 */
	public AccountGameInfo getAccountGameInfo() {
		return aAccountGameInfo;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getCurrentGameUUID(){
		return aAccountGameInfo.getCurrentGame().getVal1();
	}
		
	/**
	 * @return this account's ID
	 */
	public UUID getID(){
		return aUUID;
	}

	/**
	 * @return account password
	 */
	public String getPassword(){
		return aPassword;
	}
	
	/**
	 * @return the Account's userName
	 */
	public String getUsername() {
		return aUsername;
	}
	
	@Override public String toString(){
		return new Gson().toJson(this);
	}
	
	@Override public boolean equals(Object pObject){
		if(!(pObject instanceof Account)){
			return false;
		}
		Account lTargetAccount = (Account)pObject;
		return aUUID == lTargetAccount.getID();
	}

	@Override public int hashCode() {
		return aUUID.hashCode();
	}
}
