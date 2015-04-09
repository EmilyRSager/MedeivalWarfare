package mw.server.network.controllers;

import java.io.IOException;
import java.util.UUID;

import mw.server.admin.AccountManager;
import mw.server.network.communication.ClientCommunicationController;
import mw.server.network.lobby.GameID;
import mw.shared.clientcommands.AcknowledgementCommand;
import mw.shared.clientcommands.ErrorMessageCommand;
import test.mw.server.gamelogic.GameMarshaller;

public class SaveGameController {
	/**
	 * this is saving and then exiting the game because you can't linger on 
	 * @charles AccountUUID is needed to print the message, we can remove it if we don't want it 
	 */
	public static void saveGame(UUID pRequestingAccountID, GameID pGameID){
		try {
			for(UUID lAccountID : pGameID.getParticipantAccountIDs()){
				AccountManager.getInstance().saveAccountData(AccountManager.getInstance().getAccount(lAccountID));
				GameMarshaller.saveGame(pGameID);
				ClientCommunicationController.sendCommand(pRequestingAccountID,
						new AcknowledgementCommand("Your game was succefully saved."));
			}
			
		} catch (IOException e) {
			ClientCommunicationController.sendCommand(pRequestingAccountID,
					new ErrorMessageCommand("Failed to save the game, you lose all your shit goodbye mofo suck my dick and the boobs"));
			
		}
	}
}
