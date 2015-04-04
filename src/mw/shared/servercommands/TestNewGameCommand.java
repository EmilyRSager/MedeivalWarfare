package mw.shared.servercommands;

import mw.server.network.controllers.AdminCommandController;


public class TestNewGameCommand extends AbstractServerCommand {
	private final String aType = "TestNewGameCommand";

	@Override
	public void execute(Integer pPlayerID) throws Exception {
		AdminCommandController.getInstance().testSentGameCommand();
	}
}
