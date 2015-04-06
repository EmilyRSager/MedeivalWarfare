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
	private Set<UUID> aWaitingClients;
	private int aRequestedNumClients;
	
	/*
	 * Constructor
	 */
	public GameLobby(int pRequestedNumClients){
		aWaitingClients = new HashSet<UUID>();
		aRequestedNumClients = pRequestedNumClients;
	}

	/**
	 * adds client pClient to the waiting queue if she is not already waiting.
	 * @param pClientID
	 */
	public void addClient(UUID pClientID){
		if(!aWaitingClients.contains(pClientID)){
			aWaitingClients.add(pClientID);
		}
	}
	
	/**
	 * @return true if there are sufficient clients for a game
	 */
	public boolean containsSufficientClientsForGame(){
		return aWaitingClients.size() >= aRequestedNumClients;
	}
	
	/**
	 * @return the a set of Clients at the head of the waiting queue
	 */
	public Set<UUID> getClients(){
		return aWaitingClients;
	}
}
