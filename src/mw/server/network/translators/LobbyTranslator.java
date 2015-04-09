package mw.server.network.translators;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import mw.server.admin.AccountManager;
import mw.server.network.lobby.GameRoom;
import mw.shared.SharedCreatedGame;

/**
 * @author cbloom7
 * Translates a server game lobby into information userful for the client - a shared game lobby that maintains mappings
 * between game names and usernames participating in that game.
 */
public class LobbyTranslator {
	public static Set<SharedCreatedGame> translateGameRooms(HashMap<String, GameRoom> pGameRooms){
		Set<SharedCreatedGame> lCreatedGames = new HashSet<SharedCreatedGame>();
		
		for(String lGameName : pGameRooms.keySet()){
			lCreatedGames.add(translateGameRoom(lGameName, pGameRooms.get(lGameName)));
		}
		
		return lCreatedGames;
	}
	
	public static SharedCreatedGame translateGameRoom(String pGameName, GameRoom pGameRoom){
		Set<String> lParticipantUsernames = new HashSet<String>();
		for(UUID lAccountID : pGameRoom.getClients()){
			lParticipantUsernames.add(AccountManager.getInstance().getAccount(lAccountID).getUsername());
		}
		return new SharedCreatedGame(pGameName, lParticipantUsernames);
	}
}
