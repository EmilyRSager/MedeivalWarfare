package mw.client.controller;

import mw.client.app.MainApplication;
import mw.client.model.ModelTile;
import mw.shared.SharedTile;

public final class GameCommandHandler {


	public static void createNewGame(SharedTile[][] newTiles)
	{
		final int width = newTiles.length;
		final int height = newTiles[0].length;
		ModelTile[][] gameTiles = new ModelTile[width][height];
		for (int i=0; i<width; i++)
		{
			for (int j=0; j<height; j++)
			{
				gameTiles[i][j] = NetworkModelTranslator.translateSharedTile(newTiles[i][j]);
			}
		}
		MainApplication.newGame(gameTiles);
		MainApplication.startDisplay();
	}
	
	public static void newTileState(SharedTile newState)
	{
		NewStateApplier.applyChanges(CurrentClientState.getCurrentGame(), newState);
	}
	
}