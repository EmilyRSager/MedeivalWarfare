package mw.server.network.controllers;

import java.io.IOException;
import java.util.UUID;

import test.mw.server.gamelogic.GameMarshaller;
import mw.server.network.communication.ClientCommunicationController;
import mw.server.network.lobby.GameID;
import mw.server.network.lobby.GameLobby;
import mw.server.network.mappers.AccountMapper;
import mw.server.network.mappers.ClientChannelMapper;
import mw.server.network.mappers.GameMapper;
import mw.server.network.mappers.PlayerMapper;
import mw.server.network.translators.LobbyTranslator;
import mw.shared.clientcommands.ErrorMessageCommand;
import mw.shared.clientcommands.RelaunchLobbyCommand;

public class TerminationController {

	/**
	 * 
	 * @param pGameID
	 * @throws IOException 
	 */
	public static void closeGame(GameID pGameID) {
		try {
			GameMarshaller.saveGame(pGameID);
		} catch (IOException e) {
			for(UUID lAccountID : pGameID.getParticipantAccountIDs()){
				ClientCommunicationController.sendCommand(lAccountID,
						new ErrorMessageCommand("Someone exited the game and save failed. You're progress was lost. Sorry."));
			}
		}
		
		for(UUID lAccountID : pGameID.getParticipantAccountIDs()){
			GameMapper.getInstance().removeGameID(lAccountID);
			PlayerMapper.getInstance().removePlayer(lAccountID);
			ClientCommunicationController.sendCommand(lAccountID, new RelaunchLobbyCommand(LobbyTranslator.translateGameLobby(GameLobby.getInstance())));
		}
	}
	
	/**
	 * 
	 * @param pClientID
	 */
	public static void closeClientConnection(Integer pClientID){
		if(clientIsLoggedIn(pClientID)){
			UUID lClosingAccountID = AccountMapper.getInstance().getAccountID(pClientID);
			if(clientIsCurrentlyInAGame(lClosingAccountID)){
				GameID lGameID = GameMapper.getInstance().getGameID(lClosingAccountID);
				closeGame(lGameID);
			}
		}
		
		else{
			ClientChannelMapper.getInstance().getChannel(pClientID).shutDown();
		}
	}
	
	/**
	 * 
	 * @param pClientID
	 * @return
	 */
	private static boolean clientIsLoggedIn(Integer pClientID){
		return AccountMapper.getInstance().containsClientIDKey(pClientID);
	}
	
	/**
	 * 
	 * @param pAccountID
	 * @return
	 */
	private static boolean clientIsCurrentlyInAGame(UUID pAccountID){
		return GameMapper.getInstance().getGameID(pAccountID) != null;
	}
}
