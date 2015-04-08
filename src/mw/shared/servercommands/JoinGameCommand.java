package mw.shared.servercommands;

import java.util.UUID;

import mw.server.network.controllers.GameInitializationController;

public class JoinGameCommand extends AbstractAuthenticatedServerCommand {
	private final String aType = "JoinGameCommand";
	private String aGameName;
	
	public JoinGameCommand(String pGameName) {
		aGameName = pGameName;
	}

	@Override
	protected void doExecution(UUID pAccountID) throws Exception {
		GameInitializationController.getInstance().joinGame(pAccountID, aGameName);
	}
	
}
