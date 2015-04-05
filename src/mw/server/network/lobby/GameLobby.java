/**
 * @author Charlie Bloomfield
 * Mar 5, 2015
 */

package mw.server.network.lobby;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.UUID;

/**
 * @singleton
 * Maintains a set of users waiting to join a Game, and creates a game when there are enough users to play.
 * For simplicity, the game lobby supports creating games when there are enough available players. Later on,
 * this will be extended to provide functionality for specific GameRequests.
 */
public class GameLobby {
	private static final int GAME_SIZE = 2;
	private Queue<UUID> aWaitingAccounts;

	/*
	 * Constructor
	 */
	public GameLobby(){
		aWaitingAccounts = new LinkedList<UUID>();
	}

	/**
	 * adds client pAccount to the waiting queue if she is not already waiting.
	 * @param pAccountID
	 */
	public void addAccount(UUID pAccountID){
		if(!aWaitingAccounts.contains(pAccountID)){
			aWaitingAccounts.add(pAccountID);
		}
	}
	
	/**
	 * @return true if there are sufficient clients for a game
	 */
	public boolean containsSufficientPlayersForGame(){
		return aWaitingAccounts.size() >= GAME_SIZE;
	}
	
	/**
	 * @return the a set of Accounts at the head of the waiting queue
	 */
	public Set<UUID> removeAvailableAccounts(){
		Set<UUID> lAvailableAccounts = new HashSet<UUID>();
		
		for(int i = 0; i < GAME_SIZE; i++){
			lAvailableAccounts.add(aWaitingAccounts.remove());
		}
		
		return lAvailableAccounts;
	}
}
