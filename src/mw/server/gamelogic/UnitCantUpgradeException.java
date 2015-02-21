/**
 * @author emilysager
 */
package mw.server.gamelogic;

public class UnitCantUpgradeException extends Exception {

	private String aMessage; 
	
	public UnitCantUpgradeException(String pMessage)
	{
		aMessage = pMessage; 
	}
}
