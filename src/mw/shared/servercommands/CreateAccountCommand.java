package mw.shared.servercommands;

import mw.server.network.controllers.AdminCommandController;
import mw.server.network.exceptions.IllegalCommandException;

public class CreateAccountCommand extends AbstractServerCommand {
	private final String aType = "CreateAccountCommand";
	private String aUsername;
	private String aPassword;
	
	public CreateAccountCommand(String pUsername, String pPassword) {
		aUsername = pUsername;
		aPassword = pPassword;
	}
	
	@Override
	public void execute(Integer pClientID) throws IllegalCommandException {
		AdminCommandController.getInstance().createUser(pClientID, aUsername, aPassword);
	}

}
