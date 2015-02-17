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
	private ArrayList<Client> aClients;
	
	public ClientList(){
		aClients = new ArrayList<Client>();
	}
	
	public synchronized void add(Client pClient){
		aClients.add(pClient);
		
		System.out.println("Client " + pClient.getClientNumber() + "added to list.");
	}
	
	public synchronized void remove(Client pClient){
		aClients.remove(pClient);
	}
}
