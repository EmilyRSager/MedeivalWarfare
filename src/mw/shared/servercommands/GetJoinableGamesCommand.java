package mw.shared.servercommands;

import java.util.UUID;

import mw.server.admin.AccountManager;
import mw.server.network.controllers.GameInitializationController;
import mw.server.network.mappers.AccountMapper;

public class GetJoinableGamesCommand extends AbstractServerCommand {
	private final String aType = "GetJoinableGamesCommand";

	@Override
	public void execute(Integer pClientID) throws Exception {
		UUID lRequestingAccountID = AccountMapper.getInstance().getAccountID(pClientID);
		GameInitializationController.getInstance().getJoinableGames(lRequestingAccountID);
	}

}
