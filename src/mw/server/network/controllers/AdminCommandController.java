package mw.server.network.controllers;

import java.util.Set;
import java.util.UUID;

import mw.server.admin.AccountManager;
import mw.server.network.communication.ClientCommunicationController;
import mw.server.network.mappers.AccountMapper;
import mw.server.network.mappers.ClientChannelMapper;
import mw.shared.clientcommands.AbstractClientCommand;
import mw.shared.clientcommands.AccountCreatedCommand;
import mw.shared.clientcommands.MessageReceivedCommand;
import mw.shared.clientcommands.UserAuthenticatedCommand;

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


	public void authenticateUser(Integer pClientID, String pUsername, String pPassword) {
		try{
			UUID lAccountID = AccountManager.getInstance().getAccountID(pUsername, pPassword);
			AccountMapper.getInstance().put(pClientID, lAccountID);
			ClientCommunicationController.sendCommand(lAccountID, new UserAuthenticatedCommand(true));
		} catch (IllegalArgumentException e) {
			ClientChannelMapper.getInstance().getChannel(pClientID).sendCommand(new UserAuthenticatedCommand(false));
		}
	}

	public void createUser(Integer pClientID, String pUsername, String pPassword){
		try{
			UUID lAccountID = AccountManager.getInstance().createAccount(pUsername, pPassword);
			AccountMapper.getInstance().put(pClientID, lAccountID);
			ClientCommunicationController.sendCommand(lAccountID, new AccountCreatedCommand(true));
		} catch (IllegalArgumentException e) {
			ClientChannelMapper.getInstance().getChannel(pClientID).sendCommand(new AccountCreatedCommand(false));
		}
	}

	/**
	 * Reports an error message to client pAccountID.
	 * @param pErrorString
	 * @param pAccountID
	 * @unused
	 */
	public void reportErrorMessage(String pErrorMessage, UUID pAccountID){
		AbstractClientCommand lClientCommand = null;
		//TODO
		
		Integer lClientID = AccountMapper.getInstance().getClientID(pAccountID);
		ClientChannelMapper.getInstance().getChannel(lClientID).sendCommand(lClientCommand);
	}

	/**
	 * Distributes a chat message to all the clients in the set pAccountIDs.
	 * @param pString
	 * @param pAccountIDs
	 */
	public void distributeChatMessage(String pMessage, Set<UUID> pAccountIDs){
		AbstractClientCommand lClientCommand = new MessageReceivedCommand(pMessage);

		for(UUID lAccountID : pAccountIDs){
			ClientCommunicationController.sendCommand(lAccountID, lClientCommand);
		}
	}
}
