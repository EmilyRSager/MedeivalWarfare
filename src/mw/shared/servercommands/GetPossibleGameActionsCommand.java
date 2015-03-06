package mw.shared.servercommands;

import mw.server.network.GameManager;
import mw.server.network.GetPossibleActionsController;
import mw.shared.SharedCoordinates;

public class GetPossibleActionsCommand extends AbstractServerCommand {
	private final String aType = "GetPossibleGamesActionsCommand";
	private SharedCoordinates aSharedCoordinates;
	
	/**
	 * Constructor
	 * @param pSharedCoordinates
	 */
	public GetPossibleActionsCommand(SharedCoordinates pSharedCoordinates) {
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
				GameManager.getInstance().getGame(pClientID),
				aSharedCoordinates
				);
		
	}

}
