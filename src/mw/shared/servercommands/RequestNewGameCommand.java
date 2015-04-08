/**
 * @author Charlie Bloomfield
 * Mar 5, 2015
 */

package mw.shared.servercommands;

import java.util.UUID;

import mw.server.network.controllers.GameInitializationController;
import mw.server.network.exceptions.IllegalCommandException;

public class RequestNewGameCommand extends AbstractAuthenticatedServerCommand {
	private final String aType = "RequestNewGameCommand";
	private String aGameName;
	private int aNumPlayers;
	
	public RequestNewGameCommand(String pGameName, int pNumPlayers) {
		aGameName = pGameName;
		aNumPlayers = pNumPlayers;
	}
	
	@Override
	protected void doExecution(UUID pAccountID) throws IllegalCommandException {
		GameInitializationController.requestNewGame(pAccountID, aGameName, aNumPlayers);
	}
}
