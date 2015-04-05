package mw.shared.servercommands;

import java.util.UUID;

import mw.server.network.controllers.GetPossibleActionsController;
import mw.server.network.mappers.AccountMapper;
import mw.server.network.mappers.GameMapper;
import mw.shared.Coordinates;

public class GetPossibleGameActionsCommand extends AbstractServerCommand {
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
	public void execute(Integer pClientID) throws Exception {
		UUID lAccountID = AccountMapper.getInstance().getAccountID(pClientID);
		GetPossibleActionsController.getPossibleActions(
				pClientID,
				GameMapper.getInstance().getGame(lAccountID),
				aSharedCoordinates
				);
		
	}

}
