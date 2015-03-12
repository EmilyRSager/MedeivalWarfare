package mw.shared.servercommands;

import mw.server.network.controllers.GetPossibleActionsController;
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
	
	/**
	 * 
	 * @param pClientID
	 * @return true if move is valid.
	 */
	@Override
	public boolean isValid(Integer pClientID) {
		return true;
	}

	@Override
	public void execute(Integer pClientID) {
		GetPossibleActionsController.getPossibleActions(
				pClientID,
				GameMapper.getInstance().getGame(pClientID),
				aSharedCoordinates
				);
		
	}

}
