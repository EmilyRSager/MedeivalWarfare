package mw.server.network.lobby;

import java.util.HashMap;
import java.util.UUID;

/**
 * @author cbloom7
 * Aggregates and manages game rooms.
 */
public class GameLobby {
	private HashMap<String, GameRoom> aGameRooms;
	
	/**
	 * Constructor
	 */
	public GameLobby(){
		aGameRooms = new HashMap<String, GameRoom>();
	}
	
	/**
	 * Creates a new game room with the parameter name and puts the requesting account in the room
	 * @param pRequestingAccountID
	 * @param pGameName
	 * @param pNumRequestedClients
	 */
	public void createNewGameRoom(UUID pRequestingAccountID, String pGameName, int pNumRequestedClients){
		GameRoom lNewGameRoom = new GameRoom(pNumRequestedClients);
		lNewGameRoom.addClient(pRequestingAccountID);
		aGameRooms.put(pGameName, new GameRoom(pNumRequestedClients));
	}
	
	/**
	 * @param pGameName
	 * @return the game room with the specified name
	 */
	public GameRoom getGameRoom(String pGameName){ 
		return aGameRooms.get(pGameName);
	}
	
	/**
	 * @param pGameName
	 */
	public void removeGameRoom(String pGameName){
		aGameRooms.remove(pGameName);
	}
}
