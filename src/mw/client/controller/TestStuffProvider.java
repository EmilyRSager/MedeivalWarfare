package mw.client.controller;

import mw.client.app.MainApplication;
import mw.shared.SharedColor;
import mw.shared.Coordinates;
import mw.shared.SharedTile;

public final class TestStuffProvider {
	
	
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
		return getNewSharedTiles(MainApplication.DEFAULT_MAP_WIDTH, MainApplication.DEFAULT_MAP_HEIGHT);
	}
	
	public static SharedTile getNewTile(int x, int y)
	{
		return new SharedTile(SharedColor.GREY, new Coordinates(x, y), SharedTile.Terrain.GRASS, false);
	}
	
}