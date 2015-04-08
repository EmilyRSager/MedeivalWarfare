package mw.server.gamelogic.exceptions;

import mw.server.network.exceptions.IllegalCommandException;

/**
 * @author cbloom7
 * Exception class solely for 'tagging purposes'. All game exceptions reported to end users must extend this class
 */
public class GameLogicException extends IllegalCommandException{
	
	public GameLogicException(){
		super("Emily didn't write a good error message.");
	}
	
	public GameLogicException(String pMessage) {
		super(pMessage);
	}
}
