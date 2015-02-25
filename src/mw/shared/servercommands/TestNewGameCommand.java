package mw.shared.servercommands;

import mw.server.network.AdminCommandHandler;


public class TestNewGameCommand extends AbstractServerCommand {
	private final String aType = "TestNewGameCommand";

	@Override
	public boolean isValid(int pPlayerID) {
		return true;
	}

	@Override
	public void execute(int pPlayerID) {
		AdminCommandHandler.getInstance().testSentGameCommand();
	}
}
