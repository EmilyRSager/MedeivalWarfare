package mw.shared.clientcommands;

import mw.client.controller.menuing.MenuActionSender;

public class UserAuthenticatedCommand extends AbstractClientCommand {
	private final String aType = "UserAuthenticatedCommand";
	private boolean aAuthenticated;
	
	public UserAuthenticatedCommand(boolean pAuthenticated) {
		aAuthenticated = pAuthenticated;
	}
	
	@Override
	public boolean isValid() {
		return true;
	}

	@Override
	public void execute() {
		MenuActionSender.setAuthenticationResult(aAuthenticated);
	}

}
