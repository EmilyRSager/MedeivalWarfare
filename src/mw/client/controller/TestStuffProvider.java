package mw.client.controller;

import mw.shared.SharedColor;
import mw.shared.SharedCoordinates;
import mw.shared.SharedTile;

public final class TestStuffProvider {

	private static final int GENEREAL_WIDTH = 10;
	private static final int GENERAL_HEIGHT = 10;
	
	public static SharedTile[][] getNewSharedTiles(int width, int height)
	{
		SharedTile[][] array = new SharedTile[width][height];
		for (int i=0; i<width; i++)
		{
			for (int j=0; j<height; j++)
			{
				array[i][j] = getNewTile(i, j);
			}
		}
		return array;
	}

	public static SharedTile[][] getNewTiles() {
		return getNewSharedTiles(GENEREAL_WIDTH, GENERAL_HEIGHT);
	}
	
	public static SharedTile getNewTile(int x, int y)
	{
		return new SharedTile(SharedColor.GREY, new SharedCoordinates(x, y), SharedTile.Terrain.GRASS, false);
	}
	
}