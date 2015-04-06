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
 * 
 * TODO This class could be an observer of current game and be updated of necessary information.
 */
public class Account {
	private UUID aUUID;
	private String aUsername;
	private String aPassword;
	private AccountGameInfo aAccountGameInfo;
	
	public AccountGameInfo getaAccountGameInfo() {
		return aAccountGameInfo;
	}

	public void setaAccountGameInfo(AccountGameInfo aAccountGameInfo) {
		this.aAccountGameInfo = aAccountGameInfo;
	}
	
	public String getCurrentGameUUID(){
		return aAccountGameInfo.getCurrentGame().getVal1();
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
	 * @return the Game that this account is currently playing in. In the future, this should support
	 * multiple Game instances. Maybe this can be implemented with a Set<Game> class attribute.
	 */
	
	
	/**
	 * Set Account's current game
	 * @param pCurrentGame
	 */
	
	
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
	
	/**
	 * @param pUsername
	 * @param pPassword
	 * @return true if this account has credentials equal to pUsername and pPassword
	 */
	public boolean hasCredentials(String pPassword){
		return aPassword.equals(pPassword);
	}
	
	/**
	 * @return number of games this account has won
	 */
	public int getWins() {
		return aAccountGameInfo.getaWins();
	}

	/**
	 * @return number of games this account has lost
	 */
	public int getLosses() {
		return aAccountGameInfo.getaLosses();
	}
	
	/**
	 * increments number of wins
	 */
	public void incrementWins(){
		int oldWins = getWins();
		aAccountGameInfo.setaWins(oldWins++);
	}
	
	/**
	 * increment number of losses
	 */
	public void incrementLosses(){
		int oldLosses = getLosses();
		aAccountGameInfo.setaLosses(oldLosses++);
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
}
