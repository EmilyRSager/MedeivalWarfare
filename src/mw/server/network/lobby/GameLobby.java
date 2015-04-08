package mw.server.network.lobby;

import java.util.HashMap;
import java.util.Set;
import java.util.UUID;

/**
 * @author cbloom7
 * Aggregates and manages game rooms.
 */
public class GameLobby {
	private static GameLobby aGameLobbyInstance;
	private HashMap<String, GameRoom> aGameRooms;
	
	/**
	 * Constructor
	 */
	private GameLobby(){
		aGameRooms = new HashMap<String, GameRoom>();
	}
	
	/**
	 * @return static singleton instance
	 */
	public static GameLobby getInstance(){
		if(aGameLobbyInstance == null){
			aGameLobbyInstance = new GameLobby();
		}
		return aGameLobbyInstance;
	}
	
	
	/**
	 * @return
	 */
	public HashMap<String, GameRoom> getGameRooms(){
		return aGameRooms;
	}
	
	/**
	 * 
	 * @param pGameName
	 * @return
	 */
	public GameRoom getGameRoom(String pGameName){
		return aGameRooms.get(pGameName);
	}
	
	/**
	 * Creates a new game room with the parameter name and puts the requesting account in the room
	 * @param pRequestingAccountID
	 * @param pGameName
	 * @param pNumRequestedClients
	 */
	public GameRoom createNewGameRoom(String pGameName, int pNumRequestedClients){
		GameRoom lCreatedGameRoom = new GameRoom(pNumRequestedClients);
		aGameRooms.put(pGameName, lCreatedGameRoom);
		return lCreatedGameRoom;
	}
	
	/**
	 * @param pGameRoom
	 * @param pGameName
	 */
	public void addGameRoom(String pGameName, GameRoom pGameRoom){
		aGameRooms.put(pGameName ,pGameRoom);
	}
	
	/**
	 * @param pGameName
	 * @return
	 */
	public boolean roomIsComplete(String pGameName){
		return aGameRooms.get(pGameName).containsSufficientClientsForGame();
	}
	
	/**
	 * @param pGameName
	 * @return
	 */
	public boolean roomIsEmpty(String pGameName){
		return aGameRooms.get(pGameName).isEmpty();
	}
	
	/**
	 * @param pJoiningAccountID
	 * @param pGameName
	 */
	public void addParticipantToGame(UUID pJoiningAccountID, String pGameName) throws IllegalArgumentException{
		aGameRooms.get(pGameName).addClient(pJoiningAccountID);
	}
	
	/**
	 * @param pGameName
	 * @return
	 */
	public Set<UUID> getParticipantAccounts(String pGameName){
		return aGameRooms.get(pGameName).getClients();
	}
	
	/**
	 * @param pGameName
	 */
	public GameRoom removeGameRoom(String pGameName){
		return aGameRooms.remove(pGameName);
	}
}
