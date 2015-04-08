package mw.server.network.exceptions;

public class IllegalCommandException extends Exception{
	
	public IllegalCommandException() {
		super("Charlie didn't write a good error message.");
	}
	
	public IllegalCommandException(String pMessage){
		super(pMessage);
	}
}
