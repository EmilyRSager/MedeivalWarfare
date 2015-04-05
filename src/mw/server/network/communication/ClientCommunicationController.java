package mw.server.network.communication;

import java.util.UUID;

import mw.server.network.mappers.AccountMapper;
import mw.server.network.mappers.ClientChannelMapper;
import mw.shared.clientcommands.AbstractClientCommand;

/**
 * @author cbloom7
 * Handles sending commands to clients. 
 */
public class ClientCommunicationController {
	
	/**
	 * Sends a command to the client associated with the parameter account ID if that client is online. Otherwise,
	 * the command is not sent.
	 * @param pUUID
	 * @param pClientCommand
	 */
	public static void sendCommand(UUID pUUID, AbstractClientCommand pClientCommand){
		if(AccountMapper.getInstance().containsAccountIDKey(pUUID)){
			Integer lClientID = AccountMapper.getInstance().getClientID(pUUID);
			ClientChannel lClientChannel = ClientChannelMapper.getInstance().getChannel(lClientID);
			lClientChannel.sendCommand(pClientCommand);
		}
	}
}
