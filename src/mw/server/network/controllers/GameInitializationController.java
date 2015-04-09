/**
 * @author Charlie Bloomfield
 * Mar 5, 2015
 */

package mw.server.network.controllers;

import java.io.IOException;
import java.util.Set;
import java.util.UUID;

import mw.server.admin.AccountManager;
import mw.server.gamelogic.exceptions.TooManyPlayersException;
import mw.server.network.communication.ClientCommunicationController;
import mw.server.network.lobby.GameID;
import mw.server.network.lobby.GameLobby;
import mw.server.network.lobby.GameRoom;
import mw.server.network.lobby.LoadableGameRoom;
import mw.server.network.translators.LobbyTranslator;
import mw.shared.SharedCreatedGame;
import mw.shared.SharedGameLobby;
import mw.shared.clientcommands.AcknowledgementCommand;
import mw.shared.clientcommands.DisplayGameLobbyCommand;
import mw.shared.clientcommands.DisplayNewGameRoomCommand;
import mw.shared.clientcommands.InviteToLoadedGameCommnad;
import test.mw.server.gamelogic.GameMarshaller;

/**
 * Manages game requests by maintaining a set of game lobbies and creating games when there
 * are sufficient clients available to create a Game. Handles assigning clients to GamePlayers
 * and informing the clients of their Colors.
 */
public class GameInitializationController {
	
	/**
	 * Gets the loaded game, finds out the allowable Account UUIDs and then creates a LoadableGameRoom
	 * @param pAccountID
	 * @param pGameID
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */
	public static void createLoadableGame(UUID pRequestingAccountID, String pGameName) throws ClassNotFoundException, IOException{
		GameID lLoadedGameID = GameMarshaller.loadSavedGame(pGameName);
		int lNumParticipants = lLoadedGameID.getParticipantAccountIDs().size();
				
		LoadableGameRoom lLoadableGameRoom = new LoadableGameRoom(lNumParticipants, lLoadedGameID);
		lLoadableGameRoom.addClient(pRequestingAccountID);
		GameLobby.getInstance().addGameRoom(lLoadedGameID.getName(), lLoadableGameRoom);
			
		for(UUID lParticpantAccountID : lLoadedGameID.getParticipantAccountIDs()){
			if (!lParticpantAccountID.equals(pRequestingAccountID)) {
				ClientCommunicationController.sendCommand(lParticpantAccountID, 
						new InviteToLoadedGameCommnad(LobbyTranslator.translateGameRoom(pGameName, lLoadableGameRoom)));
			}
			else{
				ClientCommunicationController.sendCommand(pRequestingAccountID, 
						new DisplayNewGameRoomCommand(LobbyTranslator.translateGameRoom(pGameName, lLoadableGameRoom)));
			}
		}
	}
	
	/**
	 * @return a set of game lobbies that are open and waiting for players to join
	 */
	public static void getJoinableGames(UUID pRequestingAccountID){
		Set<SharedCreatedGame> lCreatedGames = LobbyTranslator.translateGameRooms(GameLobby.getInstance().getGameRoomsAvailableToClient(pRequestingAccountID));
		Set<String> lLoadableGameNames = AccountManager.getInstance().getAccount(pRequestingAccountID).getAccountGameInfo().getActiveGamesNames();
		SharedGameLobby lSharedGameLobby = new SharedGameLobby(lCreatedGames, lLoadableGameNames);
		ClientCommunicationController.sendCommand(pRequestingAccountID, new DisplayGameLobbyCommand(lSharedGameLobby));
	}

	/**
	 * Creates a new game if there are sufficient Accounts waiting to play. Otherwise, an 
	 * acknowledgement is sent to the Account informing her to wait.
	 * @param pAccountID
	 */
	public static void requestNewGame(UUID pRequestingAccountID, String pGameName, int pNumRequestedPlayers){
		GameRoom lCreatedGameRoom = GameLobby.getInstance().createNewGameRoom(pGameName, pNumRequestedPlayers);
		GameLobby.getInstance().addParticipantToGame(pRequestingAccountID, pGameName);
		ClientCommunicationController.sendCommand(pRequestingAccountID, new DisplayNewGameRoomCommand(LobbyTranslator.translateGameRoom(pGameName, lCreatedGameRoom)));
	}
	
	/**
	 * @param pAccountID
	 * @param pGameName
	 */
	public static void joinGame(UUID pJoiningAccountID, String pGameName) throws TooManyPlayersException{
		GameLobby.getInstance().addParticipantToGame(pJoiningAccountID, pGameName);
		GameRoom lGameRoom = GameLobby.getInstance().getGameRoom(pGameName);
		ClientCommunicationController.sendCommand(pJoiningAccountID, new DisplayNewGameRoomCommand(LobbyTranslator.translateGameRoom(pGameName, lGameRoom)));
		if(GameLobby.getInstance().roomIsComplete(pGameName)){
			GameRoom lReadyGameRoom = GameLobby.getInstance().getGameRoom(pGameName);
			lReadyGameRoom.initializeGame(pGameName);
			GameLobby.getInstance().removeGameRoom(pGameName);
		}
		else{
			ClientCommunicationController.sendCommand(pJoiningAccountID,
					new AcknowledgementCommand("Game \"" + pGameName + "\" successfully joined. Awaiting other players"));
		}
	}
}
