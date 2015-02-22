/**
 * @author Abhishek Gupta
 */
//TODO: at the moment the class is not complete and needs to be flushed out to match the needs as they come up

package mw.shared.networkmessages;

public class ClientToServerMessage extends AbstractNetworkMessage{
	
	private final String myType = "ClientToServerMessage";
	private String aMessage;
	
	
	public ClientToServerMessage(String pMessage) {
		aMessage = pMessage;
	}
	
	@Override
	public boolean isValid(int pPlayerID){
		return true;
	}
	
	@Override
	public void execute(int pPlayerID){
		//TODO: add the way that this message is to be executed
	}
	
	public String getaMessage() {
		return aMessage;
	}
	
	
	
	
	
	
}
