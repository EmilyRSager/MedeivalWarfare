/**
 * @author emilysager
 */
package mw.server.gamelogic.exceptions;

@SuppressWarnings("serial")
public class CantUpgradeException extends Exception {

	@SuppressWarnings("unused")
	private String aMessage; 
	
	public CantUpgradeException(String pMessage)
	{
		aMessage = pMessage; 
	}
}
