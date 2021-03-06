/**
 * @author Charlie Bloomfield, Abhishek Gupta
 * Feb 20, 2015
 */
package mw.shared.servercommands;

import java.util.Set;
import java.util.UUID;

import mw.server.network.controllers.AdminCommandController;
import mw.server.network.exceptions.IllegalCommandException;
import mw.server.network.mappers.ClientChannelMapper;

/**
 * Initiates sending a message to several recipients by calling the proper server controllers.
 */
public class SendMessageCommand extends AbstractAuthenticatedServerCommand {
	
	private final String aType = "SendMessageCommand";
	private String aMessage;
	private Set<Integer> aRecipientClientIDs;
	
	/**
	 * Constructor.
	 * @param pMessage
	 * @param pRecipientClientIDs
	 */
	public SendMessageCommand(String pMessage, Set<Integer> pRecipientClientIDs) {
		aMessage = pMessage;
		aRecipientClientIDs = pRecipientClientIDs;
	}
	/**
	 * The point of this test class is to write this function. A TestServerMessage object will be created
	 * on the client, serialized, sent over the network to the server, deserialized by the server, and then
	 * passed to the ServerMessageHandler. At this point, the ServerMessageHandler will call isValid on this
	 * class, which for now returns true by default. Then it will invoke execute. The body of this call must
	 * request the Clients that correspond to each ClientID in aRecipientClientIds, and then invoke
	 * sendMessage() on each of those clients. In this case, the parameter pClientID is useless. It may
	 * actually be completely useless in the future as well.
	 * 
	 * @param int pClientID, the identification number the client who sent this message to the server.
	 */
	@Override
	protected void doExecution(UUID pAccountID) throws IllegalCommandException {
		AdminCommandController.getInstance().distributeChatMessage(aMessage, aRecipientClientIDs);
	}
}
