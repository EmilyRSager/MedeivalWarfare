/**
 * @author Charlie Bloomfield
 * Feb 24, 2015
 */

package mw.shared.servercommands;

import mw.server.network.controllers.AdminCommandController;
import mw.shared.SharedColor;
import mw.shared.SharedCoordinates;
import mw.shared.SharedTile;
import mw.shared.SharedTile.Terrain;

public class TestSharedTileCommand extends AbstractServerCommand {
	private final String aType = "TestSharedTileCommand";

	@Override
	public boolean isValid(Integer pPlayerID) {
		return true;
	}

	@Override
	public void execute(Integer pPlayerID) {
		SharedTile lSharedTile = new SharedTile(SharedColor.GREY, new SharedCoordinates(0, 0), Terrain.GRASS, false);
		AdminCommandController.getInstance().testSendSharedTileCommand(lSharedTile);
	}
}
