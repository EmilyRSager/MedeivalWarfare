package mw.shared.clientcommands;

import java.util.Collection;

import mw.client.controller.netmodel.GameCommandHandler;
import mw.shared.SharedTile;

public class UpdateAggregateTilesCommand extends AbstractClientCommand {
	private final String aType = "UpdateAggregateTilesCommand";
	private Collection<SharedTile> aUpdatedTiles;
	
	public UpdateAggregateTilesCommand(Collection<SharedTile> pUpdatedTiles) {
		aUpdatedTiles = pUpdatedTiles;
	}
	
	@Override
	public void execute() {
		for(SharedTile lSharedTile : aUpdatedTiles){
			GameCommandHandler.newTileState(lSharedTile);
		}
	}
}
