package mw.shared.clientcommands;

<<<<<<< HEAD
import mw.server.gamelogic.PossibleActions;
=======
import mw.client.controller.ActionInterpreter;
import mw.server.gamelogic.CollectionOfPossibleActions;
import mw.shared.SharedPossibleGameActions;
>>>>>>> origin/master


public class DisplayPossibleGameActionsCommand extends AbstractClientCommand {
	private final String aType = "DisplayPossibleActionsCommand";
<<<<<<< HEAD
	private PossibleActions aPossibleActions;
=======
	private SharedPossibleGameActions aSharedPossibleGameActions;
>>>>>>> origin/master
	
	/**
	 * Constructor
	 * @param pPossibleActions
	 */
<<<<<<< HEAD
	public DisplayPossibleGameActionsCommand(PossibleActions pPossibleActions) {
		aPossibleActions = pPossibleActions;
=======
	public DisplayPossibleGameActionsCommand(SharedPossibleGameActions pSharedPossibleGameActions) {
		aSharedPossibleGameActions = pSharedPossibleGameActions;
>>>>>>> origin/master
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
