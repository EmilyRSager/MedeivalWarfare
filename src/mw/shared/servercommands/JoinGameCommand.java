package mw.shared.servercommands;

import java.util.UUID;

import mw.server.network.controllers.GameInitializationController;
import mw.server.network.exceptions.IllegalCommandException;

public class JoinGameCommand extends AbstractAuthenticatedServerCommand {
	private final String aType = "JoinGameCommand";
	private String aGameName;
	
	public JoinGameCommand(String pGameName) {
		aGameName = pGameName;
	}

	@Override
	protected void doExecution(UUID pAccountID) throws IllegalCommandException {
		GameInitializationController.getInstance().joinGame(pAccountID, aGameName);
	}
	
}
