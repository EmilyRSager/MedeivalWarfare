package mw.shared.servercommands;

import java.util.UUID;

import mw.server.network.controllers.EndTurnController;
import mw.server.network.exceptions.IllegalCommandException;
import mw.server.network.mappers.GameMapper;

public class EndTurnCommand extends AbstractAuthenticatedServerCommand{
	private final String aType = "EndTurnCommand";

	@Override
	protected void doExecution(UUID pAccountID) throws IllegalCommandException {
		EndTurnController.endTurn(GameMapper.getInstance().getGame(pAccountID), pAccountID);
	}
}
