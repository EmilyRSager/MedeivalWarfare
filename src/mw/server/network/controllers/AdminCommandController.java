package mw.server.network.controllers;

import java.util.Set;
import java.util.UUID;

import mw.server.network.mappers.AccountMapper;
import mw.server.network.mappers.ClientChannelMapper;
import mw.shared.clientcommands.AbstractClientCommand;
import mw.shared.clientcommands.MessageReceivedCommand;

/**
 * @singleton
 * Handles commands that do no involve changes in the state of GameLogic classes.
 * For example, this class handles sending chat and error messages.
 */
public class AdminCommandController {
	private static AdminCommandController aAdminMessageDistributor;
	/**
	 * Singleton implementation
	 * @return the static instance of AdminMessageDistributor
	 */
	public static AdminCommandController getInstance(){
		if(aAdminMessageDistributor == null){
			aAdminMessageDistributor = new AdminCommandController();
		}
		
		return aAdminMessageDistributor;
	}
	
	/**
	 * Reports an error message to client pAccountID.
	 * @param pErrorString
	 * @param pAccountID
	 */
	public void reportErrorMessage(String pErrorMessage, UUID pAccountID){
		AbstractClientCommand lAccountCommand = null;
		Integer lClientID = AccountMapper.getInstance().getClientID(pAccountID);
		
		ClientChannelMapper.getInstance().getChannel(lClientID).sendCommand(lAccountCommand);
	}
	
	/**
	 * Distributes a chat message to all the clients in the set pAccountIDs.
	 * @param pString
	 * @param pAccountIDs
	 */
	public void distributeChatMessage(String pMessage, Set<UUID> pAccountIDs){
		AbstractClientCommand lClientCommand = new MessageReceivedCommand(pMessage);
		
		for(UUID lAccountID : pAccountIDs){
			if(AccountMapper.getInstance().containsAccountIDKey(lAccountID)){
				Integer lClientID = AccountMapper.getInstance().getClientID(lAccountID);
				ClientChannelMapper.getInstance().getChannel(lClientID).sendCommand(lClientCommand);
			}
		}
	}
}
