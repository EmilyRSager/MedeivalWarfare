package mw.shared.servercommands;

import java.util.UUID;

import mw.server.admin.AccountManager;
import mw.server.network.controllers.GameInitializationController;
import mw.server.network.exceptions.IllegalCommandException;
import mw.server.network.mappers.AccountMapper;

public class GetJoinableGamesCommand extends AbstractAuthenticatedServerCommand {
	private final String aType = "GetJoinableGamesCommand";

	@Override
	protected void doExecution(UUID pAccountID) throws IllegalCommandException {
		GameInitializationController.getInstance().getJoinableGames(pAccountID);
	}
}
