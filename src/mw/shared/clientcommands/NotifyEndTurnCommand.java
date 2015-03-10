package mw.shared.clientcommands;

public class NotifyEndTurnCommand extends AbstractClientCommand {
	private final String aType = "NotifyEndTurnCommand";
	
	@Override
	public boolean isValid() {
		return true;
	}

	@Override
	public void execute() {
		// TODO Call client controller method to notify that it's no longer this client's turn
		
	}

}
