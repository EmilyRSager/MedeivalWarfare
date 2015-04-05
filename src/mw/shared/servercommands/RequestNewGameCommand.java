/**
 * @author Charlie Bloomfield
 * Mar 5, 2015
 */

package mw.shared.servercommands;

import java.util.UUID;

import mw.server.network.controllers.GameInitializationController;
import mw.server.network.mappers.AccountMapper;

/**
 * 
 */
public class RequestNewGameCommand extends AbstractServerCommand {
	private final String aType = "RequestNewGameCommand";

	/**
	 * Adds pClientID to the global GameLobby. This will initiate the creation of a Game
	 * if there are sufficient Clients available for a Game. Otherwise, this Client
	 * will be informed that it must wait.
	 * @param pClientID
	 * @see mw.shared.servercommands.AbstractServerCommand#execute(java.lang.Integer)
	 */
	@Override
	public void execute(Integer pClientID) throws Exception{
		UUID lAccountID = AccountMapper.getInstance().getAccountID(pClientID);
		GameInitializationController.getInstance().requestNewGame(lAccountID);
	}

}
