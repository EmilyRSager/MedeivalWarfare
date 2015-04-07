/**
 * @author Abhishek Gupta
 */

package mw.server.network.lobby;

import java.util.ArrayList;
import java.util.UUID;

public class LoadableGameRoom extends GameRoom{
	
	//the attribute helps to ensure that only the correct accounts can join the game
	private ArrayList<UUID> allowableAccountUUIDs;
	
	/**
	 * Constructor
	 * @param pNumRequestedClients
	 * @param pAllowableAccountUUIDs
	 */
	public LoadableGameRoom(int pNumRequestedClients, ArrayList<UUID> pAllowableAccountUUIDs) {
		
		super(pNumRequestedClients);
		this.allowableAccountUUIDs = pAllowableAccountUUIDs;
	}
	
	/**
	 * 
	 * @param pAccountUUID
	 */
	public void addAllowableClient(UUID pAccountUUID){
		if (allowableAccountUUIDs.contains(pAccountUUID)) {
			this.aWaitingClients.add(pAccountUUID);
		}
		else {
			throw new IllegalArgumentException("you are not allowed to join this game, exit now");
		}
	}
	
	
}
