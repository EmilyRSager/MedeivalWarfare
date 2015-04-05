package mw.shared.servercommands;

import java.util.UUID;

import mw.server.network.controllers.EndTurnController;
import mw.server.network.mappers.AccountMapper;
import mw.server.network.mappers.GameMapper;

public class EndTurnCommand extends AbstractServerCommand{
	private final String aType = "EndTurnCommand";
	
	@Override
	public void execute(Integer pClientID) throws Exception {
		UUID lAccountID = AccountMapper.getInstance().getAccountID(pClientID);
		EndTurnController.endTurn(GameMapper.getInstance().getGame(lAccountID), lAccountID);
	}
}
