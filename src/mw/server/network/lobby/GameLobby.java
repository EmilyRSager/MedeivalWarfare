package mw.server.network.lobby;

import java.util.HashMap;
import java.util.Set;
import java.util.UUID;

/**
 * @author cbloom7
 * Aggregates and manages game rooms.
 */
public class GameLobby {
	private HashMap<String, GameRoom> aGameRooms;
	private HashMap<String, LoadableGameRoom> aLoadableGameRooms;
	/**
	 * Constructor
	 */
	public GameLobby(){
		aGameRooms = new HashMap<String, GameRoom>();
		aLoadableGameRooms = new HashMap<String, LoadableGameRoom>();
	}
	
	public void addGameRoom(GameRoom pGameRoom, String pGameName){
		aGameRooms.put(pGameName ,pGameRoom);

	}
	
	public void addLoadableGameRoom(LoadableGameRoom pLoadableGameRoom){
		aLoadableGameRooms.put(pLoadableGameRoom.returnGameID().getaName() ,pLoadableGameRoom);
	}
	
	/**
	 * @return
	 */
	public HashMap<String, GameRoom> getGameRooms(){
		return aGameRooms;
	}
	
	/**
	 * Creates a new game room with the parameter name and puts the requesting account in the room
	 * @param pRequestingAccountID
	 * @param pGameName
	 * @param pNumRequestedClients
	 */
	public void createNewGameRoom(String pGameName, int pNumRequestedClients){
		aGameRooms.put(pGameName, new GameRoom(pNumRequestedClients));
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
	public void addParticipantToGame(UUID pJoiningAccountID, String pGameName){
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
