/**
 * @author emilysager
 */
package mw.server.gamelogic.exceptions;

@SuppressWarnings("serial")
public class CantUpgradeException extends GameLogicException {

	@SuppressWarnings("unused")
	private String aMessage; 
	
	public CantUpgradeException(String pMessage)
	{
		aMessage = pMessage; 
	}
}
