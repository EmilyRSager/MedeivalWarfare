package mw.server.gamelogic.exceptions;

@SuppressWarnings("serial")
public class NotEnoughIncomeException extends GameLogicException {

	public NotEnoughIncomeException(){
		super("You do not have enough income to support that operation.");
	}
	
	public NotEnoughIncomeException (String pMessage){
		super(pMessage);
	}
}
