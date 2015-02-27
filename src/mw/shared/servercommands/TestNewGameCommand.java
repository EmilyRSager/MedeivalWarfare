package mw.shared.servercommands;

import mw.server.network.AdminCommandHandler;


public class TestNewGameCommand extends AbstractServerCommand {
	private final String aType = "TestNewGameCommand";

	@Override
	public boolean isValid(Integer pPlayerID) {
		return true;
	}

	@Override
	public void execute(Integer pPlayerID) {
		AdminCommandHandler.getInstance().testSentGameCommand();
	}
}
