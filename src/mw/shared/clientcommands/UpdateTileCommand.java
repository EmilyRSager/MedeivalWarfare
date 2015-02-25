/**
 * @author Charlie Bloomfield
 * Feb 24, 2015
 */

package mw.shared.clientcommands;

import mw.client.controller.GameCommandHandler;
import mw.shared.SharedTile;

public class UpdateTileCommand extends AbstractClientCommand {
	private final String aType = "UpdateTileCommand";
	private SharedTile aSharedTile;
	
	/**
	 * Constructor
	 * @param pSharedTile the tile that changed state
	 */
	public UpdateTileCommand(SharedTile pSharedTile) {
		aSharedTile = pSharedTile;
	}
	
	@Override
	public boolean isValid() {
		return true;
	}

	@Override
	public void execute() {
		GameCommandHandler.newTileState(aSharedTile);
	}
}
