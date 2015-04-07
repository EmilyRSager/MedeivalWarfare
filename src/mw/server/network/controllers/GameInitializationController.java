/**
 * @author Charlie Bloomfield
 * Mar 5, 2015
 */

package mw.server.network.controllers;

import java.util.UUID;

import mw.server.gamelogic.state.Game;
import mw.server.gamelogic.state.GameID;
import mw.server.network.communication.ClientCommunicationController;
import mw.server.network.lobby.GameLobby;
import mw.server.network.lobby.GameRoom;
import mw.server.network.lobby.LoadableGameRoom;
import mw.server.network.translators.LobbyTranslator;
import mw.shared.SharedGameLobby;
import mw.shared.clientcommands.AcknowledgementCommand;
import mw.shared.clientcommands.DisplayGameLobbyCommand;
import mw.shared.clientcommands.DisplayNewGameRoomCommand;
import mw.shared.clientcommands.InviteToLoadedGameCommnad;

/**
 * Manages game requests by maintaining a set of game lobbies and creating games when there
 * are sufficient clients available to create a Game. Handles assigning clients to GamePlayers
 * and informing the clients of their Colors.
 */
public class GameInitializationController {
	private static GameInitializationController aGameInitializationController;
	private GameLobby aGameLobby;
	
	/**
	 * Constructor
	 */
	private GameInitializationController(){
		aGameLobby = new GameLobby();
	}

	/**
	 * Singleton implementation
	 * @return static GameInitializationController instance
	 */
	public static GameInitializationController getInstance(){
		if(aGameInitializationController == null){
			aGameInitializationController = new GameInitializationController();
		}

		return aGameInitializationController;
	}
	
	/**
	 * Gets the loaded game, finds out the allowable Account UUIDs and then creates a LoadableGameRoom
	 * @param pAccountID
	 * @param pGameID
	 */
	public void createLoadableGame(UUID pRequestingAccountID, GameID pGameID){
		Game lGame = pGameID.getaGame();
		int lNumRequestedClients = lGame.getPlayers().size();
		String lLoadedGameName = pGameID.getaName();
				
		LoadableGameRoom lLoadableGameRoom = new LoadableGameRoom(lNumRequestedClients, pGameID);
		aGameLobby.addGameRoom(pGameID.getaName(), lLoadableGameRoom);
			
		for(UUID lParticpantAccountID :lLoadableGameRoom.getClients()){
			if (lParticpantAccountID != pRequestingAccountID) {
				ClientCommunicationController.sendCommand(lParticpantAccountID, 
						new InviteToLoadedGameCommnad(LobbyTranslator.translateGameRoom(lLoadedGameName, lLoadableGameRoom)));
			}
			else{
				ClientCommunicationController.sendCommand(pRequestingAccountID, 
						new DisplayNewGameRoomCommand(LobbyTranslator.translateGameRoom(lLoadedGameName, lLoadableGameRoom)));
			}
		}
	}
	
	/**
	 * @return a set of game lobbies that are open and waiting for players to join
	 */
	public void getJoinableGames(UUID pRequestingAccountID){
		SharedGameLobby lSharedGameLobby = LobbyTranslator.translateGameLobby(aGameLobby);
		ClientCommunicationController.sendCommand(pRequestingAccountID, new DisplayGameLobbyCommand(lSharedGameLobby));
	}

	/**
	 * Creates a new game if there are sufficient Accounts waiting to play. Otherwise, an 
	 * acknowledgement is sent to the Account informing her to wait.
	 * @param pAccountID
	 */
	public void requestNewGame(UUID pRequestingAccountID, String pGameName, int pNumRequestedPlayers){
		GameRoom lCreatedGameRoom = aGameLobby.createNewGameRoom(pGameName, pNumRequestedPlayers);
		aGameLobby.addParticipantToGame(pRequestingAccountID, pGameName);
		ClientCommunicationController.sendCommand(pRequestingAccountID, new DisplayNewGameRoomCommand(LobbyTranslator.translateGameRoom(pGameName, lCreatedGameRoom)));
	}
	
	/**
	 * @param pAccountID
	 * @param pGameName
	 */
	public void joinGame(UUID pJoiningAccountID, String pGameName){
		aGameLobby.addParticipantToGame(pJoiningAccountID, pGameName);
		GameRoom lGameRoom = aGameLobby.getGameRoom(pGameName);
		ClientCommunicationController.sendCommand(pJoiningAccountID, new DisplayNewGameRoomCommand(LobbyTranslator.translateGameRoom(pGameName, lGameRoom)));
		if(aGameLobby.roomIsComplete(pGameName)){
			GameRoom lReadGameRoom = aGameLobby.removeGameRoom(pGameName);
			lReadGameRoom.initializeGame(pGameName);
		}
		else{
			ClientCommunicationController.sendCommand(pJoiningAccountID,
					new AcknowledgementCommand("Game \"" + pGameName + "\" successfully joined. Awaiting other players"));
		}
	}
}
