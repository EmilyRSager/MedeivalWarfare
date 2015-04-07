/**
 * @author Abhishek Gupta
 */

package mw.server.admin;

import java.util.ArrayList;
import java.util.Collection;

import mw.server.gamelogic.enums.Color;
import mw.util.Tuple2;

public class AccountGameInfo {
	private int aWins, aLosses;
	
	//the current game is also stored inside the active games 
	private Tuple2<String, Color> aCurrentGame; //associating the account with the game and the player
	//list of active games 
	//String is the name of the game and color is the color of the player in that game
	private Collection<Tuple2<String, Color>> aActiveGames;
	//list of past games
	private Collection<Tuple2<String, Color>> aPastGames;
	
	/**
	 * Constructor
	 */
	public AccountGameInfo() {
		aCurrentGame = null;
		aActiveGames = new ArrayList<Tuple2<String,Color>>();
		aPastGames = new ArrayList<Tuple2<String,Color>>();
	}
	
	/**
	 * 
	 * @return
	 */
	public int getWins(){
		return aWins;
	}
	
	/**
	 * 
	 * @return
	 */
	public int getLosses(){
		return aLosses;
	}
	
	/**
	 * 
	 * @return
	 */
	public void incrementWins() {
		aWins ++;
	}

	/**
	 * 
	 * @return
	 */
	public void incrementLosses() {
		aLosses ++;
	}

	/**
	 * 
	 * @return
	 */
	public Tuple2<String, Color> getCurrentGame() {
		return aCurrentGame;
	}
	
	/**
	 * 
	 * @param pCurrentGame
	 */
	public void setCurrentGame(Tuple2<String, Color> pCurrentGame) {
		aCurrentGame = pCurrentGame;
	}
	
	/**
	 * 
	 * @param aGame
	 */
	public void addToActiveGames(Tuple2<String, Color> aGame){
		aActiveGames.add(aGame);
	}
	
	/**
	 * 
	 * @param aGame
	 */
	public void removeFromActiveGames(Tuple2<String, Color> aGame){
		aActiveGames.remove(aGame);
	}
	
	/**
	 * 
	 * @param aGame
	 */
	public void addToPastGames(Tuple2<String, Color> aGame){
		aPastGames.add(aGame);
	}
	
	/**
	 * 
	 * @param aGame
	 */
	public void removeFromPastGames(Tuple2<String, Color> aGame){
		aPastGames.remove(aGame);
	}
}
