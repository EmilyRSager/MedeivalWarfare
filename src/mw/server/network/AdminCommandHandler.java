package mw.server.network;

import java.util.Set;

import mw.shared.clientcommands.AbstractClientCommand;
import mw.shared.clientcommands.MessageReceivedCommand;

/**
 * @singleton
 * Handles commands that do no involve changes in the state of GameLogic classes.
 * For example, this class handles sending chat messages, error messages, etc.
 */
public class AdminCommandHandler {
	private static AdminCommandHandler aAdminMessageDistributor;
	private ClientChannelManager aClientChannelManager;
	
	private AdminCommandHandler(){
		aClientChannelManager = ClientChannelManager.getInstance();
	}
	
	/**
	 * Singleton implementation
	 * @return the static instance of AdminMessageDistributor
	 */
	public static AdminCommandHandler getInstance(){
		if(aAdminMessageDistributor == null){
			aAdminMessageDistributor = new AdminCommandHandler();
		}
		
		return aAdminMessageDistributor;
	}
	
	/**
	 * Reports an error message to client pClientID.
	 * @param pErrorString
	 * @param pClientID
	 */
	public void reportErrorMessage(String pErrorMessage, Integer pClientID){
		AbstractClientCommand lClientCommand = null;
		
		aClientChannelManager.getChannel(pClientID).sendCommand(lClientCommand);
	}
	
	/**
	 * Distributes a chat message to all the clients in the set pClientIDs.
	 * @param pString
	 * @param pClientIDs
	 */
	public void distributeChatMessage(String pMessage, Set<Integer> pClientIDs){
		AbstractClientCommand lClientCommand = 
				new MessageReceivedCommand(pMessage);
		
		for(ClientChannel lClientOnServer : aClientChannelManager.getChannelSet(pClientIDs)){
			lClientOnServer.sendCommand(lClientCommand);
		}
	}
}
