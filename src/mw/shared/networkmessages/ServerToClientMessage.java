/**
 * @author Abhishek Gupta
 */
//TODO: at the moment the class is not complete and needs to be flushed out to match the needs as they come up
package mw.shared.networkmessages;

public class ServerToClientMessage {
	private final String myType = "ServerToClientMessage";
	private String aMessage;
	
	public ServerToClientMessage(String pMessage) {
		aMessage=pMessage;
	}
	
	public boolean isValid(int pPlayerID){
		return true;
	}
	
	public void execute(int pPlayerID){
		//TODO: add here the code that would be appropriate to handle the messages
	}
	
	public String getaMessage() {
		return aMessage;
	}

	
	
}
