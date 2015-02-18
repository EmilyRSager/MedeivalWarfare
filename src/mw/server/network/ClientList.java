/**
 * 
 */
package mw.server.network;

import java.util.ArrayList;

/**
 * @author Charlie Bloomfield
 * Feb 16, 2015
 */
public class ClientList {
	private ArrayList<ServerClient> aClients;
	
	public ClientList(){
		aClients = new ArrayList<ServerClient>();
	}
	
	public synchronized void add(ServerClient pClient){
		aClients.add(pClient);
		
		System.out.println("Client " + pClient.getClientNumber() + " added to list.");
	}
	
	public synchronized void remove(ServerClient pClient){
		aClients.remove(pClient);
	}
}
