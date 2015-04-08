package mw.shared.servercommands;

import java.util.UUID;

import mw.server.network.exceptions.IllegalCommandException;
import mw.server.network.mappers.AccountMapper;

public class LogoutCommand extends AbstractAuthenticatedServerCommand{
	private final String aType = "LogoutCommand";

	@Override
	protected void doExecution(UUID pAccountID) throws IllegalCommandException {
		AccountMapper.getInstance().remove(pAccountID);
	}
}
