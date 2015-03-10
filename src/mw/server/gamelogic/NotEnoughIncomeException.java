package mw.server.gamelogic;

@SuppressWarnings("serial")
public class NotEnoughIncomeException extends Exception {

	@SuppressWarnings("unused")
	private int aDeficit; 
	
	public NotEnoughIncomeException (int pDeficit)
	{
		aDeficit = pDeficit; 
	}
}
