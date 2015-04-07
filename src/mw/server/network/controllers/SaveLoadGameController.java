package mw.server.network.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

import test.mw.server.gamelogic.SaveGame;
import mw.server.admin.Account;
import mw.server.admin.AccountManager;
import mw.server.gamelogic.state.Game;
import mw.server.gamelogic.state.GameID;
import mw.server.gamelogic.state.Player;
import mw.server.network.communication.ClientCommunicationController;
import mw.server.network.mappers.GameMapper;
import mw.server.network.mappers.PlayerMapper;
import mw.shared.clientcommands.ErrorMessageCommand;

public class SaveLoadGameController {
	/**
	 * this is saving and then exiting the game because you can't linger on 
	 * @charles AccountUUID is needed to print the message, we can remove it if we don't want it 
	 */
	public static void saveGame(Game pGame, String pGameName, UUID pAccountUUID ){
		try {
			ArrayList<Player> list = (ArrayList<Player>) pGame.getPlayers();
			ArrayList<UUID> listOfAccountUUIDs = new ArrayList<UUID>();
			for(Player p: list){
				listOfAccountUUIDs.add(PlayerMapper.getInstance().getAccount(p));
			}
			GameID lGameID = new GameID(pGame, pGameName, listOfAccountUUIDs);
			//following line can be removed if the account data is already saved
			AccountManager.getInstance().saveAccountData(AccountManager.getInstance().getAccount(pAccountUUID));
			SaveGame.SaveMyGame(lGameID);
			
		} catch (IOException e) {
			ClientCommunicationController.sendCommand(pAccountUUID, new ErrorMessageCommand("Failed to save the game, you lose all your shit goodbye mofo suck my dick and the boobs"));
			
		}
	}
	
	public static void loadGame(String pGameName, UUID pAccountUUID){
		try {
			GameID lGameID=SaveGame.returnSavedGame(pGameName);
			GameInitializationController.getInstance().loadSavedGame(pAccountUUID, lGameID);
		} catch (ClassNotFoundException e) {
			ClientCommunicationController.sendCommand(pAccountUUID, new ErrorMessageCommand("Failed to load the game, you lose all your shit goodbye mofo suck my dick and the boobs"));
			
		} catch (IOException e) {
			ClientCommunicationController.sendCommand(pAccountUUID, new ErrorMessageCommand("Failed to load the game, you lose all your shit goodbye mofo suck my dick and the boobs"));
			
		}
		
	}
	
}
