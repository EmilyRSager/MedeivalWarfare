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
	
	public static ClientManager getInstance(){
		if(aClientManagerInstance == null){
			aClientManagerInstance = new ClientManager();
		}
		
		return aClientManagerInstance;
	}
	
	public void add(ClientOnServer pClientOnServer){
		Integer lClientID = pClientOnServer.getClientID();
		
		aClientOnServerMap.put(lClientID, pClientOnServer);
	}
}
