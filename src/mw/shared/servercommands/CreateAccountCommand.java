package mw.shared.servercommands;

import java.util.UUID;

import mw.server.admin.AccountManager;
import mw.server.network.controllers.AdminCommandController;
import mw.server.network.exceptions.IllegalCommandException;
import mw.server.network.mappers.AccountMapper;
import mw.server.network.mappers.ClientChannelMapper;
import mw.shared.clientcommands.AccountCreatedCommand;

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
