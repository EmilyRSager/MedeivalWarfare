package mw.server.network.translators;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import mw.server.admin.AccountManager;
import mw.server.network.lobby.GameLobby;
import mw.server.network.lobby.GameRoom;
import mw.shared.SharedCreatedGame;
import mw.shared.SharedGameLobby;

/**
 * @author cbloom7
 * Translates a server game lobby into information userful for the client - a shared game lobby that maintains mappings
 * between game names and usernames participating in that game.
 */
public class LobbyTranslator {
	public static SharedGameLobby translateGameLobby(GameLobby pGameLobby){
		Set<SharedCreatedGame> lCreatedGames = new HashSet<SharedCreatedGame>();
		HashMap<String, GameRoom> lGameRooms = pGameLobby.getGameRooms();
		
		for(String lGameName : lGameRooms.keySet()){
			Set<String> lParticipantUsernames = new HashSet<String>();
			
			for(UUID lUUID : lGameRooms.get(lGameName).getClients()){
				lParticipantUsernames.add(AccountManager.getInstance().getAccount(lUUID).getUsername());
			}
			lCreatedGames.add(new SharedCreatedGame(lGameName, lParticipantUsernames));
		}
		
		return new SharedGameLobby(lCreatedGames);
	}
}
