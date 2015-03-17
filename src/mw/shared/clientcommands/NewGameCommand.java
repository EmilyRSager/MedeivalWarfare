/**
 * @author Charlie Bloomfield
 * Feb 24, 2015
 */

package mw.shared.clientcommands;

import mw.client.controller.netmodel.GameCommandHandler;
import mw.shared.SharedTile;

public class NewGameCommand extends AbstractClientCommand {
	private final String aType = "NewGameCommand";
	private SharedTile[][] aGameMap;
	
	/**
	 * Constructor
	 * @param pGameMap
	 */
	public NewGameCommand(SharedTile[][] pGameMap) {
		aGameMap = pGameMap;
	}

	@Override
	public boolean isValid() {
		return true;
	}

	@Override
	public void execute() {
		GameCommandHandler.createNewGame(aGameMap);
	}
}
