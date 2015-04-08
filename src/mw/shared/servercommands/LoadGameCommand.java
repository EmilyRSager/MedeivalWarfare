package mw.shared.servercommands;

import java.util.UUID;

import mw.server.network.controllers.SaveLoadGameController;
import mw.server.network.mappers.AccountMapper;

public class LoadGameCommand extends AbstractServerCommand{
	private final String aType = "LoadGameCommand";
	private String aGameName;
	
	/**
	 * Constructors
	 * @param pGameName
	 */
	public LoadGameCommand(String pGameName) {
		aGameName = pGameName;
	}
	
	@Override
	public void execute(Integer pClientID) throws Exception {
		UUID lRequestingAccountID = AccountMapper.getInstance().getAccountID(pClientID);
		SaveLoadGameController.loadGame(aGameName, lRequestingAccountID);
	}

}
