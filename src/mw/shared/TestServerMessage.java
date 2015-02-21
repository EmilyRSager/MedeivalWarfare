/**
 * @author Charlie Bloomfield
 * Feb 20, 2015
 */
package mw.shared;

import java.util.Set;

import mw.server.network.ClientManager;
import mw.server.network.ClientOnServer;

public class TestServerMessage extends AbstractServerMessage {
	
	private String aMessage;
	private Set<Integer> aRecipientCliendIDs;
	
	/**
	 * Constructor.
	 * @param pMessage
	 * @param pRecipientClientIDs
	 */
	public TestServerMessage(String pMessage, Set<Integer> pRecipientClientIDs) {
		aMessage = pMessage;
		aRecipientCliendIDs = pRecipientClientIDs;
	}
	
	/**
	 * @param int pPlayerID, the identification number the client who sent this message to the server.
	 * @see mw.shared.AbstractServerMessage#isValid(int)
	 */
	@Override
	public boolean isValid(int pPlayerID) {
		return true;
	}

	/**
	 * The point of this test class is to write this function. A TestServerMessage object will be created
	 * on the client, serialized, sent over the network to the server, deserialized by the server, and then
	 * passed to the ServerMessageHandler. At this point, the ServerMessageHandler will call isValid on this
	 * class, which for now returns true by default. Then it will invoke execute. The body of this call must
	 * request the Clients that correspond to each ClientID in aRecipientClientIds, and then invoke
	 * sendMessage() on each of those clients. In this case, the parameter pPlayerID is useless. It may
	 * actually be completely useless in the future as well.
	 * 
	 * @param int pPlayerID, the identification number the client who sent this message to the server.
	 * @see mw.shared.AbstractServerMessage#execute(int)
	 */
	@Override
	public void execute(int pPlayerID) {
		for(Integer lClientID : aRecipientCliendIDs){
			ClientOnServer lClientOnServer = ClientManager.getInstance().get(lClientID);
			lClientOnServer.testSendString(aMessage);
		}
	}
	
	/**
	 * @return aMessage 
	 */
	public String getMessage() {
		return aMessage;
	}
	
	/**
	 * @return a Set of ClientIDs that this message is to be sent to
	 */
	public Set<Integer> getRecipientCliendIDs() {
		return aRecipientCliendIDs;
	}
}
