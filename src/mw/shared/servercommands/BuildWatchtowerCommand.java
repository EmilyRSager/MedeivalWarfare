package mw.shared.servercommands;

import java.util.UUID;

import mw.server.gamelogic.controllers.GameController;
import mw.server.network.mappers.GameMapper;
import mw.shared.Coordinates;

public class BuildWatchtowerCommand extends AbstractAuthenticatedServerCommand {
	private final String aType = "BuildWatchtowerCommand";
	private Coordinates aWatchtowerCoordinates;
	
	public BuildWatchtowerCommand(Coordinates pWatchtowerCoordinates) {
		aWatchtowerCoordinates = pWatchtowerCoordinates;
	}
	
	@Override
	protected void doExecution(UUID pAccountID) {
		GameController.buildWatchtower(GameMapper.getInstance().getGame(pAccountID), aWatchtowerCoordinates);
	}
}
