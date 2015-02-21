package mw.shared;

/**
 * AbstractServerMessage represents the root class that all possible messages 
 * sent from the Client to the Server must extend.
 */
public abstract class AbstractServerMessage {
	public abstract boolean isValid(int pPlayerID);
	public abstract void execute(int pPlayerID);
}
