package mw.shared.servercommands;

import mw.server.network.controllers.TerminationController;
import mw.server.network.exceptions.IllegalCommandException;

public class ExitCommand extends AbstractServerCommand{
	private final String aType = "ExitCommand";

	@Override
	public void execute(Integer pClientID) throws IllegalCommandException {
		TerminationController.closeClientConnection(pClientID);
	}
}
