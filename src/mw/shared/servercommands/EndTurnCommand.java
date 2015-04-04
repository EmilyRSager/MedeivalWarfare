package mw.shared.servercommands;

import mw.server.network.controllers.EndTurnController;
import mw.server.network.mappers.GameMapper;

public class EndTurnCommand extends AbstractServerCommand{
	private final String aType = "EndTurnCommand";
	
	@Override
	public void execute(Integer pClientID) throws Exception {
		EndTurnController.endTurn(GameMapper.getInstance().getGame(pClientID), pClientID);
	}
}
