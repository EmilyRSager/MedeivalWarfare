package mw.shared.servercommands;

import java.util.UUID;

import mw.server.gamelogic.controllers.GameController;
import mw.server.network.exceptions.IllegalCommandException;
import mw.server.network.mappers.GameMapper;
import mw.shared.Coordinates;

public class CombineUnitsCommand extends AbstractAuthenticatedServerCommand{
	private final String aType = "CombineUnitsCommand";
	private Coordinates aSourceCoordinates, aTargetCoordinates;
	
	public CombineUnitsCommand(Coordinates pSourceCoordinates, Coordinates pTargetCoordinates) {
		aSourceCoordinates = pSourceCoordinates;
		aTargetCoordinates = pTargetCoordinates;
	}

	@Override
	protected void doExecution(UUID pAccountID) throws IllegalCommandException {
		GameController.combineVillagers(GameMapper.getInstance().getGame(pAccountID), aSourceCoordinates, aTargetCoordinates);
	}
}
