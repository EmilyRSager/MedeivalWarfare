package mw.shared;

/**
 * AbstractGameMessage represents all possible messages sent from the Client to the Server.      
 */
public abstract class AbstractGameMessage {
	public abstract boolean isValid(int pPlayerID);
	public abstract void execute(int pPlayerID);
}
