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
public class GameRoom {
	protected Set<UUID> aWaitingClients;
	protected int aNumRequestedClients;
	
	/*
	 * Constructor
	 */
	public GameRoom(int pNumRequestedClients){
		aWaitingClients = new HashSet<UUID>();
		aNumRequestedClients = pNumRequestedClients;
	}

	/**
	 * adds client pClient to the waiting queue if she is not already waiting.
	 * @param pClientID
	 */
	public void addClient(UUID pAccountID){
		aWaitingClients.add(pAccountID);
		if(!aWaitingClients.contains(pAccountID)){
			aWaitingClients.add(pAccountID);
		}
	}
	
	/**
	 * @return true if there are sufficient clients for a game
	 */
	public boolean containsSufficientClientsForGame(){
		return aWaitingClients.size() == aNumRequestedClients;
	}
	
	/**
	 * @return true if there are no clients in the room
	 */
	public boolean isEmpty(){
		return aWaitingClients.size() == 0;
	}
	
	/**
	 * @return the a set of Clients at the head of the waiting queue
	 */
	public Set<UUID> getClients(){
		return aWaitingClients;
	}
}
