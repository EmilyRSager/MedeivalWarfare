package mw.client.controller;

import mw.client.app.MainApplication;
import mw.client.model.ModelTile;
import mw.shared.SharedTile;


/**
 * The GameCommandHandler controller is responsible for handling the different commands that
 * could come from the server, modifying accordingly the model on the client
 * @author Hugo Kapp
 *
 */
public final class GameCommandHandler {

	/**
	 * Creates a new Game out of the given SharedTiles, and start displaying and interacting with the User
	 * @param newTiles the SharedTiles that give the initial state for each Tile in the new Game
	 */
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
	
	/**
	 * Applies the new state of the given SharedTile to the model
	 * @param newState the new state of a Tile
	 */
	public static void newTileState(SharedTile newState)
	{
		NewStateApplier.applyChanges(CurrentClientState.getCurrentGame(), newState);
	}
	
}