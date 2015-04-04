package mw.server.gamelogic.exceptions;

@SuppressWarnings("serial")
public class NotEnoughIncomeException extends Exception {

	String pMessage; 
	
	public NotEnoughIncomeException (String lString)
	{
		pMessage = lString; 
	}
	public NotEnoughIncomeException()
	{
		
	}
}
