/**
 * @author Charlie Bloomfield
 * Feb 20, 2015
 */
package mw.server.network;

public class InvalidGameMessageException extends Exception {
	public InvalidGameMessageException(){
		super();
	}
	
	public InvalidGameMessageException(String pMessage){
		super(pMessage);
	}
}
