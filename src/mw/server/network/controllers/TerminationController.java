package mw.server.network.controllers;

import java.io.IOException;
import java.util.HashMap;
import java.util.Set;
import java.util.UUID;

import mw.server.admin.AccountManager;
import mw.server.network.communication.ClientCommunicationController;
import mw.server.network.lobby.GameID;
import mw.server.network.lobby.GameLobby;
import mw.server.network.lobby.GameRoom;
import mw.server.network.mappers.AccountMapper;
import mw.server.network.mappers.ClientChannelMapper;
import mw.server.network.mappers.GameMapper;
import mw.server.network.mappers.PlayerMapper;
import mw.server.network.translators.LobbyTranslator;
import mw.shared.SharedCreatedGame;
import mw.shared.SharedGameLobby;
import mw.shared.clientcommands.RelaunchLobbyCommand;

public class TerminationController {

	/**
	 * 
	 * @param pGameID
	 * @throws IOException 
	 */
	public static void closeGame(GameID pGameID) {
		for(UUID lAccountID : pGameID.getParticipantAccountIDs()){
			GameMapper.getInstance().removeGameID(lAccountID);
			PlayerMapper.getInstance().removePlayer(lAccountID);

			HashMap<String, GameRoom> lAvailableGameRooms = GameLobby.getInstance().getGameRoomsAvailableToClient(lAccountID);
			Set<SharedCreatedGame> lCreatedGames = LobbyTranslator.translateGameRooms(lAvailableGameRooms);
			Set<String> lLoadableGameNames = AccountManager.getInstance().getAccount(lAccountID).getAccountGameInfo().getActiveGamesNames();

			SharedGameLobby lSharedGameLobby = new SharedGameLobby(lCreatedGames, lLoadableGameNames);
			ClientCommunicationController.sendCommand(lAccountID, new RelaunchLobbyCommand(lSharedGameLobby));
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
			AccountMapper.getInstance().remove(lClosingAccountID);
		}
		
		ClientChannelMapper.getInstance().getChannel(pClientID).shutDown();
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
