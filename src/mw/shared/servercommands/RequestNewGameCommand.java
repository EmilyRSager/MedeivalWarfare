/**
 * @author Charlie Bloomfield
 * Mar 5, 2015
 */

package mw.shared.servercommands;

import mw.server.network.controllers.GameInitializationController;

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
		GameInitializationController.getInstance().requestNewGame(pClientID);
	}

}
