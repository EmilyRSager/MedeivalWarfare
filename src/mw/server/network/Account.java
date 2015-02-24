/**
 * @author Charlie Bloomfield
 * Feb 23, 2015
 */

package mw.server.network;

import mw.client.model.Game;

/**
 * Represents a user account in the MedeivalWarfare game system. Keeps track of relevant infromation,
 * such as wins, losses, friends, etc.
 */
public class Account {
	private String aUserName;
	private String aPassword;
	private int aWins, aLosses;
	private Game aCurrentGame; //this will need to be a reference to the GameController, whatever class Emily chooses for that.
	
	public Account(String pUsername, String pPassword){
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
	public boolean hasCredentials(String pUsername, String pPassword){
		return aUserName.equals(pUsername) && aPassword.equals(pPassword);
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
		
	}
	
}
