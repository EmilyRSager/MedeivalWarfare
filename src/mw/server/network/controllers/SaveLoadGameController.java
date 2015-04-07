package mw.server.network.controllers;

import java.io.IOException;
import java.util.UUID;

import test.mw.server.gamelogic.SaveGame;
import mw.server.gamelogic.state.GameID;
import mw.server.network.communication.ClientCommunicationController;
import mw.shared.clientcommands.ErrorMessageCommand;

public class SaveLoadGameController {
	/**
	 * this is saving and then exiting the game because you can't linger on 
	 */
	public static void saveGame(GameID pGameID, UUID pAccountUUID ){
		try {
			SaveGame.SaveMyGame(pGameID);
			
		} catch (IOException e) {
			ClientCommunicationController.sendCommand(pAccountUUID, new ErrorMessageCommand("Failed to save the game, you lose all your shit goodbye mofo suck my dick and the boobs"));
			
		}
	}
	
	public static void loadGame(String pGameName, UUID pAccountUUID){
		try {
			GameID lGameID=SaveGame.returnSavedGame(pGameName);
			//GameInitializationController.getInstance().
		} catch (ClassNotFoundException e) {
			ClientCommunicationController.sendCommand(pAccountUUID, new ErrorMessageCommand("Failed to load the game, you lose all your shit goodbye mofo suck my dick and the boobs"));
			
		} catch (IOException e) {
			ClientCommunicationController.sendCommand(pAccountUUID, new ErrorMessageCommand("Failed to load the game, you lose all your shit goodbye mofo suck my dick and the boobs"));
			
		}
		
	}
	
}
