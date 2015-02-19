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
	private ArrayList<ClientRepOnServer> aClients;
	
	public ClientList(){
		aClients = new ArrayList<ClientRepOnServer>();
	}
	
	public synchronized void add(ClientRepOnServer pClient){
		aClients.add(pClient);
		
		System.out.println("Client " + pClient.getClientNumber() + " added to list.");
	}
	
	public synchronized void remove(ClientRepOnServer pClient){
		aClients.remove(pClient);
	}
}
