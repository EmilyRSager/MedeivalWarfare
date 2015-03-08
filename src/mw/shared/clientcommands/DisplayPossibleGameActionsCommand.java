/**
 * @author Charlie Blooomfield
 * March 8, 2015
 */

package mw.shared.clientcommands;

import mw.client.controller.UserActionSender;
import mw.shared.SharedPossibleGameActions;

/**
 * 
 */
public class DisplayPossibleGameActionsCommand extends AbstractClientCommand {
	private final String aType = "DisplayPossibleGameActionsCommand";
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
		UserActionSender.singleton().setPossibleActions(aSharedPossibleGameActions);
	}

}
