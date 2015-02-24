package mw.shared.servercommands;

public class AuthenticateUserCommand extends AbstractServerCommand {
	/**
	 * Constructor
	 * @param pMessage
	 */
	public AuthenticateUserCommand() {
		aType = "AuthenticateUserCommand";
	}

	@Override
	public boolean isValid(int pPlayerID) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void execute(int pPlayerID) {
		// TODO Auto-generated method stub
		
	}

}
