/**
 * @author emilysager
 */
package mw.server.gamelogic;

@SuppressWarnings("serial")
public class TooManyPlayersException extends Exception {
	
	String pMessage; 
	public TooManyPlayersException()
	{
		pMessage = "There can only be four players in Medieval Warfare."; 
	}

}