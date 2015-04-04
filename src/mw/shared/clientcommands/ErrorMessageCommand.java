package mw.shared.clientcommands;

public class ErrorMessageCommand extends AbstractClientCommand {
	private String aErrorMessage;
	
	public ErrorMessageCommand(String pErrorMessage) {
		aErrorMessage = pErrorMessage;
	}
	
	@Override
	public boolean isValid() {
		return true;
	}

	@Override
	public void execute() {
		// TODO
	}
}
