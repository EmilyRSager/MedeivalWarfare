/**
 * @author emilysager
 */
package mw.server.gamelogic;

public class CantUpgradeException extends Exception {

	private String aMessage; 
	
	public CantUpgradeException(String pMessage)
	{
		aMessage = pMessage; 
	}
}
