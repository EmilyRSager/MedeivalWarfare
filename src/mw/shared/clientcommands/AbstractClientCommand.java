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
	
	/**
	 * @return true by default. Override with more interesting condition with commands that are expected to be invalid.
	 */
	public boolean isValid(){
		return true;
	}
	
	public abstract void execute();

}
