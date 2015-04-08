package mw.shared.servercommands;

import java.util.UUID;

import mw.server.admin.AccountManager;
import mw.server.network.controllers.GameInitializationController;
import mw.server.network.mappers.AccountMapper;

public class GetJoinableGamesCommand extends AbstractAuthenticatedServerCommand {
	private final String aType = "GetJoinableGamesCommand";

	@Override
	protected void doExecution(UUID pAccountID) {
		GameInitializationController.getInstance().getJoinableGames(pAccountID);
	}
}
