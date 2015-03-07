package mw.shared.clientcommands;

import mw.client.controller.ActionInterpreter;
import mw.server.gamelogic.CollectionOfPossibleActions;
import mw.shared.SharedPossibleGameActions;


public class DisplayPossibleGameActionsCommand extends AbstractClientCommand {
	private final String aType = "DisplayPossibleActionsCommand";
	private SharedPossibleGameActions aSharedPossibleGameActions;
	
	/**
	 * Constructor
	 * @param pPossibleActions
	 */
	public DisplayPossibleGameActionsCommand(SharedPossibleGameActions pSharedPossibleGameActions) {
		aSharedPossibleGameActions = pSharedPossibleGameActions;
	}
	
	
	@Override
	public boolean isValid() {
		return true;
	}

	@Override
	public void execute() {
		ActionInterpreter.singleton().setPossibleActions(aSharedPossibleGameActions);
	}

}
