/**
 * @author Charlie Bloomfield
 * Feb 24, 2015
 */

package mw.shared.servercommands;

import mw.server.network.controllers.AdminCommandController;
import mw.shared.SharedColor;
import mw.shared.Coordinates;
import mw.shared.SharedTile;
import mw.shared.SharedTile.Terrain;

public class TestSharedTileCommand extends AbstractServerCommand {
	private final String aType = "TestSharedTileCommand";

	@Override
	public void execute(Integer pPlayerID) throws Exception {
		SharedTile lSharedTile = new SharedTile(SharedColor.GREY, new Coordinates(0, 0), Terrain.GRASS, false);
		AdminCommandController.getInstance().testSendSharedTileCommand(lSharedTile);
	}
}
