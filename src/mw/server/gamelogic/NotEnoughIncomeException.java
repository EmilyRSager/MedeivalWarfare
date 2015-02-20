package mw.server.gamelogic;

public class NotEnoughIncomeException extends Exception {

	private int aDeficit; 
	
	public NotEnoughIncomeException (int pDeficit)
	{
		aDeficit = pDeficit; 
	}
}
