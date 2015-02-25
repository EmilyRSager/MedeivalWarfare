/**
 * @author Charlie Bloomfield
 * Feb 24, 2015
 */

package mw.shared.servercommands;

import mw.server.network.AdminCommandHandler;
import mw.shared.SharedColor;
import mw.shared.SharedCoordinates;
import mw.shared.SharedTile;
import mw.shared.SharedTile.Terrain;

public class TestSharedTileCommand extends AbstractServerCommand {
	private final String aType = "TestSharedTileCommand";

	@Override
	public boolean isValid(int pPlayerID) {
		return true;
	}

	@Override
	public void execute(int pPlayerID) {
		SharedTile lSharedTile = new SharedTile(SharedColor.GREY, new SharedCoordinates(0, 0), Terrain.GRASS, false);
		AdminCommandHandler.getInstance().testSendSharedTileCommand(lSharedTile);
	}
}
