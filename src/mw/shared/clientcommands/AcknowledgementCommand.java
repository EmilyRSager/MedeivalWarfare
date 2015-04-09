/**
 * @author Charlie Bloomfield
 * Feb 23, 2015
 */

package mw.shared.clientcommands;

import mw.client.controller.menuing.MenuActionSender;

public class AcknowledgementCommand extends AbstractClientCommand{
	private final String aType = "AcknowledgementCommand";
	private final String aAcknowedgement;

	public AcknowledgementCommand(String pAcknowledgement) {
		aAcknowedgement = pAcknowledgement;
	}
	
	/**
	 * @see mw.shared.clientcommands.AbstractClientCommand#execute()
	 */
	@Override
	public void execute() {
		MenuActionSender.popUpMessage(aAcknowedgement);
	}

}
