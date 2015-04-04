/**
 * @author Charlie Bloomfield
 * Feb 23, 2015
 */

package mw.server.admin;

import java.util.List;

import com.google.gson.Gson;
import mw.client.model.Game;

/**
 * Represents a user account in the MedeivalWarfare game system. Keeps track of relevant information,
 * such as wins, losses, friends, etc.
 * 
 * TODO This class could be an observer of current game and be updated of necessary information.
 */
public class Account {
	private int aID;
	private String aUserName;
	private String aPassword;
	private int aWins, aLosses;
	private Game aCurrentGame;
	
	/**
	 * Constructor
	 * @param pUsername
	 * @param pPassword
	 */
	public Account(int pID, String pUsername, String pPassword){
		aID = pID;
		aUserName = pUsername;
		aPassword = pPassword;
		aWins = 0;
		aLosses = 0;
		aCurrentGame = null;
	}
	
	/**
	 * @return the Game that this account is currently playing in. In the future, this should support
	 * multiple Game instances. Maybe this can be implemented with a Set<Game> class attribute.
	 */
	public Game getCurrentGame(){
		return aCurrentGame;
	}
	
	/**
	 * Set Account's current game
	 * @param pCurrentGame
	 */
	public void setCurrentGame(Game pCurrentGame){
		aCurrentGame = pCurrentGame;
	}
	
	/**
	 * @return this account's ID
	 */
	public int getID(){
		return aID;
	}

	/**
	 * @return the Account's userName
	 */
	public String getUserName() {
		return aUserName;
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
		return aWins;
	}

	/**
	 * @return number of games this account has lost
	 */
	public int getLosses() {
		return aLosses;
	}
	
	/**
	 * increments number of wins
	 */
	public void incrementWins(){
		aWins++;
	}
	
	/**
	 * increment number of losses
	 */
	public void incrementLosses(){
		aLosses++;
	}
	
	@Override public String toString(){
		return new Gson().toJson(this);
	}
	
	@Override public boolean equals(Object pObject){
		if(!(pObject instanceof Account)){
			return false;
		}
		Account lTargetAccount = (Account)pObject;
		return aID == lTargetAccount.getID();
	}
}
