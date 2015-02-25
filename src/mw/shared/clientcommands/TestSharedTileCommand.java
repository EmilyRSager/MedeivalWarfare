/**
 * @author Charlie Bloomfield
 * Feb 24, 2015
 */

package mw.shared.clientcommands;

import mw.client.model.Tile.Terrain;
import mw.server.network.AdminCommandHandler;
import mw.shared.SharedColor;
import mw.shared.SharedCoordinates;
import mw.shared.SharedTile;
import mw.shared.servercommands.AbstractServerCommand;

public class TestSharedTileCommand extends AbstractServerCommand {
	private final String aType = "TestSharedTileCommand";

	@Override
	public boolean isValid(int pPlayerID) {
		return true;
	}

	@Override
	public void execute(int pPlayerID) {
		SharedTile lSharedTile = new SharedTile(SharedColor.GREY, new SharedCoordinates(0, 0), Terrain.GRASS, false)
		AdminCommandHandler.getInstance().testSendSharedTileCommand(lSharedTile);
	}
}
