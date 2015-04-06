/**
 * @author Charlie Bloomfield
 * Feb 25, 2015
 */

package mw.shared.servercommands;

import mw.server.network.controllers.AdminCommandController;

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
		AdminCommandController.getInstance().authenticateUser(pClientID, aUsername, aPassword);
	}

}
