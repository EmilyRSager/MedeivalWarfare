package mw.client.network;

import java.util.UUID;

import mw.server.admin.AccountManager;
import mw.server.network.controllers.TerminationController;
import mw.server.network.exceptions.IllegalCommandException;
import mw.server.network.mappers.GameMapper;
import mw.shared.servercommands.AbstractAuthenticatedServerCommand;

public class LeaveGameCommand extends AbstractAuthenticatedServerCommand {
	private final String aType = "LeaveGameCommand";
	
	@Override
	protected void doExecution(UUID pAccountID) throws IllegalCommandException {
		TerminationController.closeGame(GameMapper.getInstance().getGameID(pAccountID));
	}
}
