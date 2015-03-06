/**
 * @author Charlie Bloomfield
 * Feb 23, 2015
 */

package mw.shared.clientcommands;

public class AcknowledgementCommand extends AbstractClientCommand{
	private final String aType = "AcknowledgementCommand";
	private final String aAcknowedgement;

	public AcknowledgementCommand(String pAcknowledgement) {
		aAcknowedgement = pAcknowledgement;
	}
	
	/**
	 * @see mw.shared.clientcommands.AbstractClientCommand#isValid()
	 */
	@Override
	public boolean isValid() {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * @see mw.shared.clientcommands.AbstractClientCommand#execute()
	 */
	@Override
	public void execute() {
		// TODO Auto-generated method stub
		
	}

}
