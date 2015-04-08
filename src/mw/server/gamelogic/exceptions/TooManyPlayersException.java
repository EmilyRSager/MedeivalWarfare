/**
 * @author emilysager
 */
package mw.server.gamelogic.exceptions;

@SuppressWarnings("serial")
public class TooManyPlayersException extends GameLogicException {
	
	String pMessage; 
	public TooManyPlayersException()
	{
		pMessage = "There can only be four players in Medieval Warfare."; 
	}

}
