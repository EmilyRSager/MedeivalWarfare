package mw.shared.clientcommands;

public class NotifyBeginTurnCommand extends AbstractClientCommand{
	private final String aType = "BeginTurnCommand";
	
	@Override
	public boolean isValid() {
		return true;
	}

	@Override
	public void execute() {
		// TODO Call client controller method to notify that it's this client's turn
		
	}

}
