package mw.client.network;

import mw.shared.SharedCoordinates;
import mw.shared.servercommands.AuthenticateUserCommand;
import mw.shared.servercommands.GetPossibleGameActionsCommand;
import mw.shared.servercommands.RequestNewGameCommand;

/**
 * Provides access to the network so clients can send commands to the Game Server.
 */
public class NetworkController {
	private static ServerChannel aServerChannel;
	
	/**
	 * initializes aServerChannel
	 * @param pServerChannel
	 */
	public static void initializeServerChannel(ServerChannel pServerChannel){
		aServerChannel = pServerChannel;
	}
	
	/**
	 * 
	 * @param pUsername
	 * @param pPassword
	 */
	public static void authenticateUser(String pUsername, String pPassword){
		aServerChannel.sendCommand(new AuthenticateUserCommand(pUsername, pPassword));
	}
	
	
	/**
	 * 
	 */
	public static void requestNewGame(){
		aServerChannel.sendCommand(new RequestNewGameCommand());
	}
	
	/**
	 * Does something
	 * @param pAbstractServerCommand
	 */
	public static void getPossibleMoves(SharedCoordinates pSharedCoordinates){
		aServerChannel.sendCommand(new GetPossibleGameActionsCommand(pSharedCoordinates));
	}
}
