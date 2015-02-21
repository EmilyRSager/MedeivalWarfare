/**
 * @author Charlie Bloomfield
 * Feb 20, 2015
 */
package mw.server.network;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Provides a Globally accessible mapping between ClientIDs and ClientOnServer classes.
 * The ServerMessageHandler will use this class to find Client's that need to receive messages,
 * based on their IDs.
 */
public class ClientManager {
	private static ClientManager aClientManagerInstance;
	private ConcurrentMap<Integer, ClientOnServer> aClientOnServerMap;
	
	private ClientManager(){
		aClientOnServerMap = new ConcurrentHashMap<Integer, ClientOnServer>();
	}
	
	/**
	 * Singleton getInstance method
	 * @param none
	 * @return the static ClientManager instance
	 */
	public static ClientManager getInstance(){
		if(aClientManagerInstance == null){
			aClientManagerInstance = new ClientManager();
		}
		
		return aClientManagerInstance;
	}
	
	/**
	 * Maps the ID of pClientOnServer to that client.
	 * @param The ClientOnServer to be added to the map
	 * @return none
	 */
	public void add(ClientOnServer pClientOnServer){
		Integer lClientID = pClientOnServer.getClientID();
		
		aClientOnServerMap.put(lClientID, pClientOnServer);
	}
	
	/**
	 * @param the ClientID that of the ClientOnServer to be returned
	 * @return the ClientOnServer that corresponds to pClientID
	 */
	public ClientOnServer get(int pClientID){
		return aClientOnServerMap.get(pClientID);
	}
	
	/**
	 * Removes the client specified by ClientID from the
	 * @param pClientID
	 * @return none
	 */
	public void remove(int pClientID){
		aClientManagerInstance.remove(pClientID);
	}
}
