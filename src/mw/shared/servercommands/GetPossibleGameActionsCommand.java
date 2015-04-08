package mw.shared.servercommands;

import java.util.UUID;

import mw.server.network.controllers.GetPossibleActionsController;
import mw.server.network.exceptions.IllegalCommandException;
import mw.server.network.mappers.AccountMapper;
import mw.server.network.mappers.GameMapper;
import mw.shared.Coordinates;

public class GetPossibleGameActionsCommand extends AbstractAuthenticatedServerCommand {
	private final String aType = "GetPossibleGameActionsCommand";
	private Coordinates aSharedCoordinates;
	
	/**
	 * Constructor
	 * @param pSharedCoordinates
	 */
	public GetPossibleGameActionsCommand(Coordinates pSharedCoordinates) {
		aSharedCoordinates = pSharedCoordinates;
	}

	@Override
	protected void doExecution(UUID pAccountID) throws IllegalCommandException {
		GetPossibleActionsController.getPossibleActions(
				pAccountID,
				GameMapper.getInstance().getGame(pAccountID),
				aSharedCoordinates
				);
	}

}
