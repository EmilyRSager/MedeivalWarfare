package mw.shared.servercommands;

import mw.server.network.mappers.AccountMapper;

public class LogoutCommand extends AbstractServerCommand{
	private final String aType = "LogoutCommand";
	@Override
	public void execute(Integer pClientID) throws Exception {
		AccountMapper.getInstance().remove(pClientID);
	}
	
}
