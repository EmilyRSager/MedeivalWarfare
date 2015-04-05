package mw.shared.servercommands;

import java.util.UUID;

import mw.server.admin.AccountManager;
import mw.server.network.mappers.AccountMapper;

public class CreateAccountCommand extends AbstractServerCommand {
	private final String aType = "CreateAccountCommand";
	private String aUsername;
	private String aPassword;
	
	public CreateAccountCommand(String pUsername, String pPassword) {
		aUsername = pUsername;
		aPassword = pPassword;
	}
	
	@Override
	public void execute(Integer pClientID) throws Exception {
		//TODO more elegantly deal with create account error
		UUID lAccountUUID = AccountManager.getInstance().createAccount(aUsername, aPassword);
		AccountMapper.getInstance().put(pClientID, lAccountUUID);
	}

}
