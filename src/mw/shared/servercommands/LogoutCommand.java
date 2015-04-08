package mw.shared.servercommands;

import java.util.UUID;

import mw.server.network.mappers.AccountMapper;

public class LogoutCommand extends AbstractAuthenticatedServerCommand{
	private final String aType = "LogoutCommand";

	@Override
	protected void doExecution(UUID pAccountID) throws Exception {
		AccountMapper.getInstance().remove(pAccountID);
	}
}
