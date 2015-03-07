package mw.shared.clientcommands;

import mw.server.gamelogic.PossibleGameActions;
import mw.client.controller.ActionInterpreter;
import mw.shared.SharedPossibleGameActions;



public class DisplayPossibleGameActionsCommand extends AbstractClientCommand {
	private final String aType = "DisplayPossibleActionsCommand";
	private PossibleGameActions aPossibleActions;
	private SharedPossibleGameActions aSharedPossibleGameActions;

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
