package mw.shared.clientcommands;

public class UserAuthenticatedCommand extends AbstractClientCommand {
	private final String aType = "UserAuthenticatedCommand";
	private boolean aAuthenticated;
	
	public UserAuthenticatedCommand(boolean pAuthenticated) {
		aAuthenticated = pAuthenticated;
	}
	
	@Override
	public boolean isValid() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void execute() {
		MenuActionSender.setAuthenticationResult(aAuthenticated);
	}

}
