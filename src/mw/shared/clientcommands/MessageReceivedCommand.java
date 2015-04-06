/**
 * @author Charlie Bloomfield
 * Feb 23, 2015
 */

package mw.shared.clientcommands;

/**
 * Holds a Chat Message to be sent to clients.
 */
public class MessageReceivedCommand extends AbstractClientCommand {
	private final String aType = "MessageReceivedCommand";
	private String aMessage;
	
	/**
	 * 
	 * @param pMessage
	 */
	public MessageReceivedCommand(String pMessage){
		aMessage = pMessage;
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		System.out.println("[Client in MessageReceivedCommand] " + aMessage);
	}
}
