package mw.server.gamelogic.exceptions;

@SuppressWarnings("serial")
public class NotEnoughIncomeException extends GameLogicException {

	String pMessage; 
	
	public NotEnoughIncomeException (String lString)
	{
		pMessage = lString; 
	}
	public NotEnoughIncomeException()
	{
		
	}
}
