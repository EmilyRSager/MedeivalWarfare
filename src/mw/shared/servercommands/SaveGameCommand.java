package mw.shared.servercommands;

import java.util.UUID;

import mw.server.network.controllers.SaveGameController;
import mw.server.network.exceptions.IllegalCommandException;
import mw.server.network.mappers.GameMapper;

public class SaveGameCommand extends AbstractAuthenticatedServerCommand {
	private final String aType = "SaveGameCommand";
	
	@Override
	protected void doExecution(UUID pAccountID) throws IllegalCommandException {
		SaveGameController.saveGame(pAccountID, GameMapper.getInstance().getGameID(pAccountID));
	}
}
