/**
 * @author Abhishek Gupta
 */

package mw.server.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import mw.server.gamelogic.enums.Color;
import mw.util.Tuple2;

public class AccountGameInfo {
	
	private int aWins, aLosses;
	//the current game is also stored inside the active games 
	private Tuple2<UUID, Color> aCurrentGame; //associating the account with the game and the player
	//list of active games 
	private ArrayList<Tuple2<UUID, Color>> aActiveGames;
	//list of past games
	private ArrayList<Tuple2<UUID, Color>> aPastGames;
	
	
	public int getaWins() {
		return aWins;
	}
	public void setaWins(int aWins) {
		this.aWins = aWins;
	}
	public int getaLosses() {
		return aLosses;
	}
	public void setaLosses(int aLosses) {
		this.aLosses = aLosses;
	}
	public Tuple2<UUID, Color> getCurrentGame() {
		return aCurrentGame;
	}
	public void setCurrentGame(Tuple2<UUID, Color> pCurrentGame) {
		this.aCurrentGame = pCurrentGame;
	}
	
	public void addToActiveGames(Tuple2<UUID, Color> aGame){
		aActiveGames.add(aGame);
	}
	
	public void removeFromActiveGames(Tuple2<UUID, Color> aGame){
		aActiveGames.remove(aGame);
	}
	
	public void addToPastGames(Tuple2<UUID, Color> aGame){
		aPastGames.add(aGame);
	}
	
	public void removeFromPastGames(Tuple2<UUID, Color> aGame){
		aPastGames.remove(aGame);
	}
	
	
	
	
}
