package mw.shared.servercommands;

import java.util.UUID;

import mw.server.network.controllers.GameInitializationController;
import mw.server.network.mappers.AccountMapper;

public class JoinGameCommand extends AbstractServerCommand {
	private final String aType = "JoinGameCommand";
	private String aGameName;
	
	public JoinGameCommand(String pGameName) {
		aGameName = pGameName;
	}
	
	@Override
	public void execute(Integer pClientID) throws Exception {
		UUID lAccountID = AccountMapper.getInstance().getAccountID(pClientID);
		GameInitializationController.getInstance().joinGame(lAccountID, aGameName);
	}

}
