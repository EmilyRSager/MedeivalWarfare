package mw.shared.clientcommands;

public class AccountCreatedCommand extends AbstractClientCommand {
	private final String aType = "AccountCreatedCommand";
	private boolean aAccountCreated;
	
	public AccountCreatedCommand(boolean pAccountCreated) {
		aAccountCreated = pAccountCreated;
	}
	
	@Override
	public boolean isValid() {
		return true;
	}

	@Override
	public void execute() {
		MenuActionSender.setAccountCreationResult(aAccountCreated);
	}

}
