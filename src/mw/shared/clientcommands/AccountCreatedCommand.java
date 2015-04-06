package mw.shared.clientcommands;

import mw.client.controller.menuing.MenuActionSender;

public class AccountCreatedCommand extends AbstractClientCommand {
	private final String aType = "AccountCreatedCommand";
	private boolean aAccountCreated;
	
	public AccountCreatedCommand(boolean pAccountCreated) {
		aAccountCreated = pAccountCreated;
	}

	@Override
	public void execute() {
		MenuActionSender.setAccountCreationResult(aAccountCreated);
	}

}
