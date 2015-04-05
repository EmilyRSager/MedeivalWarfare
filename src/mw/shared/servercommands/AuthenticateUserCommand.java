/**
 * @author Charlie Bloomfield
 * Feb 25, 2015
 */

package mw.shared.servercommands;

import java.util.UUID;

import mw.server.admin.Account;
import mw.server.admin.AccountManager;
import mw.server.network.mappers.AccountMapper;
import mw.server.network.mappers.ClientChannelMapper;
import mw.shared.clientcommands.UserAuthenticatedCommand;

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
	public void execute(Integer pClientID) throws Exception {
		UUID lAccountID = AccountManager.getInstance().getAccountID(aUsername, aPassword);
		AccountMapper.getInstance().put(pClientID, lAccountID);
		ClientChannelMapper.getInstance().getChannel(pClientID).sendCommand(new UserAuthenticatedCommand());
	}

}
