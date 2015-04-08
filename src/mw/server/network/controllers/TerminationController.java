package mw.server.network.controllers;

import java.util.UUID;

import mw.server.network.communication.ClientCommunicationController;
import mw.server.network.lobby.GameID;
import mw.server.network.lobby.GameLobby;
import mw.server.network.mappers.GameMapper;
import mw.server.network.mappers.PlayerMapper;
import mw.server.network.translators.LobbyTranslator;
import mw.shared.clientcommands.RelaunchLobbyCommand;

public class TerminationController {

	public static void closeGame(GameID pGameID) {
		for(UUID lAccountID : pGameID.getParticipantAccountIDs()){
			GameMapper.getInstance().removeGameID(lAccountID);
			PlayerMapper.getInstance().removePlayer(lAccountID);
			ClientCommunicationController.sendCommand(lAccountID, new RelaunchLobbyCommand(LobbyTranslator.translateGameLobby(GameLobby.getInstance())));
		}
	}
	
	public static void closeClientConnection(UUID pAccountID){
		
	}
}
