/**
 * @author Charlie Bloomfield
 * Feb 25, 2015
 */

package mw.shared.servercommands;

import mw.server.admin.Account;
import mw.server.mappings.AccountManager;
import mw.server.network.controllers.AdminCommandHandler;

public class AuthenticateUserCommand extends AbstractServerCommand {
	private final String aType = "AuthenticateUserCommand";
	private String aUsername;
	private String aPassword;
	
	
	/**
	 * Constructor
	 * @param pMessage
	 */
	public AuthenticateUserCommand(String pUsername, String pPassword) {
		aUsername = pUsername;
		aPassword = pPassword;
	}

	@Override
	public boolean isValid(Integer pClientID) {
		/*
		 * Need to load from a database at server startup. This might need to be a database query.
		 */
		Account lAccount = AccountDatabase.getInstance().getAccount(aUsername);
		if(lAccount.hasCredentials(aPassword)){
			return true;
		}
		
		return false;
	}

	@Override
	public void execute(Integer pClientID) {
		AccountMapper.getInstance().putAccounts(pClientID,
				AccountDatabase.getInstance().getAccount(aUsername);
		
		AdminCommandController.getInstance().sendCommand(
				new AccountVerifiedCommand());
	}

}
