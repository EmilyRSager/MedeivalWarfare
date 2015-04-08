package mw.server.network.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

import test.mw.server.gamelogic.GameMarshaller;
import mw.server.admin.Account;
import mw.server.admin.AccountManager;
import mw.server.gamelogic.state.Game;
import mw.server.gamelogic.state.Player;
import mw.server.network.communication.ClientCommunicationController;
import mw.server.network.lobby.GameID;
import mw.server.network.mappers.GameMapper;
import mw.server.network.mappers.PlayerMapper;
import mw.shared.clientcommands.ErrorMessageCommand;

public class SaveLoadGameController {
	/**
	 * this is saving and then exiting the game because you can't linger on 
	 * @charles AccountUUID is needed to print the message, we can remove it if we don't want it 
	 */
	public static void saveGame(UUID pRequestingAccountID, GameID pGameID){
		try {
			for(UUID lAccountID : pGameID.getParticipantAccountIDs()){
				AccountManager.getInstance().saveAccountData(AccountManager.getInstance().getAccount(lAccountID));
				GameMarshaller.saveGame(pGameID);
			}
			
		} catch (IOException e) {
			ClientCommunicationController.sendCommand(pRequestingAccountID,
					new ErrorMessageCommand("Failed to save the game, you lose all your shit goodbye mofo suck my dick and the boobs"));
			
		}
	}
	
	public static void loadGame(String pGameName, UUID pAccountUUID){
		try {
			GameID lGameID = GameMarshaller.loadSavedGame(pGameName);
			GameInitializationController.createLoadableGame(pAccountUUID, lGameID);
		} catch (ClassNotFoundException e) {
			ClientCommunicationController.sendCommand(pAccountUUID, 
					new ErrorMessageCommand("Failed to load the game, you lose all your shit goodbye mofo suck my dick and the boobs"));
			
		} catch (IOException e) {
			ClientCommunicationController.sendCommand(pAccountUUID, 
					new ErrorMessageCommand("Failed to load the game, you lose all your shit goodbye mofo suck my dick and the boobs"));
		}
	}
}
