package mw.server.network;

import java.util.Set;

import mw.shared.clientmessages.AbstractClientMessage;

/**
 * @singleton
 * This class is in charge of distributing messages to clients that are not
 * directly involved with GameState. For example, this class handles sending chat messages,
 * error messages, etc. There is only one instance of this class.
 */
public class AdminMessageDistributor {
	private static AdminMessageDistributor aAdminMessageDistributor;
	private ClientManager aClientManager;
	
	private AdminMessageDistributor(){
		aClientManager = ClientManager.getInstance();
	}
	
	/**
	 * Singleton implementation
	 * @return the static instance of AdminMessageDistributor
	 */
	public static AdminMessageDistributor getInstance(){
		if(aAdminMessageDistributor == null){
			aAdminMessageDistributor = new AdminMessageDistributor();
		}
		
		return aAdminMessageDistributor;
	}
	
	/**
	 * Reports and error message to client pClientID
	 * @param pErrorString
	 * @param pClientID
	 */
	public void reportErrorMessage(String pErrorString, Integer pClientID){
		AbstractClientMessage lClientMessage = null;
		
		aClientManager.get(pClientID).sendMessage(lClientMessage);
	}
	
	/**
	 * Distributes a chat message to all the client in the set pClientIDs
	 * @param pString
	 * @param pClientIDs
	 */
	public void distributeChatMessage(String pString, Set<Integer> pClientIDs){
		AbstractClientMessage lClientMessage = null;
		
		for(ClientOnServer lClientOnServer : aClientManager.getSet(pClientIDs)){
			lClientOnServer.sendMessage(lClientMessage);
		}
	}
}
