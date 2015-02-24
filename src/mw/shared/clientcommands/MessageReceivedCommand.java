/**
 * @author Charlie Bloomfield
 * Feb 23, 2015
 */

package mw.shared.clientcommands;

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
	public boolean isValid() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		System.out.println("[Client] " + aMessage);
	}
}
