/**
 * @author Charlie Bloomfield
 * Mar 8, 2015
 */

package mw.shared.clientcommands;

import mw.client.controller.CurrentClientState;
import mw.shared.SharedColor;

/**
 * 
 */
public class SetColorCommand extends AbstractClientCommand {
	private final String aType = "SetColorCommand";
	private SharedColor aSharedColor;
	
	/**
	 * Constructor
	 * @param pSharedColor
	 */
	public SetColorCommand(SharedColor pSharedColor) {
		aSharedColor = pSharedColor;
	}

	/**
	 * @see mw.shared.clientcommands.AbstractClientCommand#execute()
	 */
	@Override
	public void execute() {
		CurrentClientState.setCurrentPlayerColor(aSharedColor);
	}

}
