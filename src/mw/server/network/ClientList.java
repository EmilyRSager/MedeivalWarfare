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
	private ArrayList<ClientOnServer> aClients;
	
	public ClientList(){
		aClients = new ArrayList<ClientOnServer>();
	}
	
	public synchronized void add(ClientOnServer pClient){
		aClients.add(pClient);
		
		System.out.println("Client " + pClient.getClientNumber() + " added to list.");
	}
	
	public synchronized void remove(ClientOnServer pClient){
		aClients.remove(pClient);
	}
}
