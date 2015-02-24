/**
 * @author Charlie Bloomfield
 * Feb 20, 2015
 */
package mw.shared.clientcommands;

/**
 * AbstractClientMessage represents the root class that all possible messages sent
 * from the Server to the Client must extend.
 */
public abstract class AbstractClientCommand {
	
	public abstract boolean isValid();
	public abstract void execute();

}
