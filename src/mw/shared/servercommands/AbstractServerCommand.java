/**
 * @author Charlie Bloomfield, Abhishek Gupta
 * Feb 21, 2015
 */

package mw.shared.servercommands;

/**
 * Represents the root class that all possible messages sent from the Client to the Server must extend.
 */
public abstract class AbstractServerCommand {
	
	/**
	 * Each class that extends AbstractServerMessage must have a unique string Type, which representing
	 * it's type. The deserialization framework relies on this type attribute to build objects of the 
	 * correct type on the server.
	 */
	
	public abstract boolean isValid(int pPlayerID);
	public abstract void execute(int pPlayerID);
}
