package mw.shared.clientcommands;

import mw.client.controller.CurrentClientState;
import mw.client.controller.netmodel.GameCommandHandler;

public class ErrorMessageCommand extends AbstractClientCommand {
	private final String aType = "ErrorMessageCommand";
	private String aErrorMessage;
	
	public ErrorMessageCommand(String pErrorMessage) {
		aErrorMessage = pErrorMessage;
	}
	

	@Override
	public void execute() {
		if (CurrentClientState.getCurrentGame() != null)
			GameCommandHandler.displayMessage(aErrorMessage);
	}
}
