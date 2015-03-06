/**
 * @author Charlie Bloomfield
 * Mar 5, 2015
 */

package mw.server.network.lobby;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

/**
 * @singleton
 * Maintains a set of users waiting to join a Game, and creates a game when there are enough users to play.
 * For simplicity, the game lobby supports creating games when there are enough available players. Later on,
 * this will be extended to provide functionality for specific GameRequests.
 */
public class GameLobby {
	private static final int GAME_SIZE = 2;
	private Queue<Integer> aWaitingClients;

	/*
	 * Constructor
	 */
	public GameLobby(){
		aWaitingClients = new LinkedList<Integer>();
	}

	/**
	 * adds client pClient to the waiting queue if she is not already waiting.
	 * @param pClientID
	 */
	public void addClient(Integer pClientID){
		if(!aWaitingClients.contains(pClientID)){
			aWaitingClients.add(pClientID);
		}
	}
	
	/**
	 * @return true if there are sufficient clients for a game
	 */
	public boolean containsSufficientClientsForGame(){
		return aWaitingClients.size() >= GAME_SIZE;
	}
	
	/**
	 * @return the a set of Clients at the head of the waiting queue
	 */
	public Set<Integer> removeAvailableClients(){
		Set<Integer> lAvailableClients = new HashSet<Integer>();
		
		for(int i = 0; i < GAME_SIZE; i++){
			lAvailableClients.add(aWaitingClients.remove());
		}
		
		return lAvailableClients;
	}
}
