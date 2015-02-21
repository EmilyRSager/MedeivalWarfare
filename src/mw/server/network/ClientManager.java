/**
 * @author Charlie Bloomfield
 * Feb 20, 2015
 */
package mw.server.network;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

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
	 * Stores a global reference between client IDs and Clients
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
}
