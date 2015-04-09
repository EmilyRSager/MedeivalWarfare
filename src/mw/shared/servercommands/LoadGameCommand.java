package mw.shared.servercommands;

import java.util.UUID;

import mw.server.network.controllers.SaveGameController;
import mw.server.network.exceptions.IllegalCommandException;

public class LoadGameCommand extends AbstractAuthenticatedServerCommand{
	private final String aType = "LoadGameCommand";
	private String aGameName;
	
	/**
	 * Constructors
	 * @param pGameName
	 */
	public LoadGameCommand(String pGameName) {
		aGameName = pGameName;
	}

	@Override
	protected void doExecution(UUID pAccountID) throws IllegalCommandException {
		SaveGameController.loadGame(aGameName, pAccountID);
	}
}
