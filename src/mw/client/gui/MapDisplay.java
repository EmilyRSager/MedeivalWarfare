package mw.client.gui;
import java.util.Observer;

import org.minueto.window.*; 

/**
 * The GameMap class contains all the functions required to visually represent the map in a game of Medieval Warfare.
 * @author Arthur Denefle
 *
 */
public class MapDisplay 
{
	private ImageTile[][] tiles;
	
	public MapDisplay(int width, int height)
	{
		tiles = new ImageTile[width][height];
		for(int i = 0; i < tiles.length; i++)
		{
			for(int j = 0; j < tiles[i].length; j++)
			{
				tiles[i][j] = new ImageTile();
			}
		}
	}
	
	public MapDisplay(ImageTile[][] givenTiles)
	{
		tiles = givenTiles;
	}
	
	public void update()
	{
		tiles[2][6].update();
	}
	
	/**
	 * The method renderMap's main functionality is to construct the map image by drawing the tiles on the window. 
	 * @param tiles a double array of Tile objects to be displayed on the map.
	 */
	public void renderMap(MinuetoWindow window)
	{
			for(int i = 0; i < tiles.length; i++)
			{
				for(int j = 0; j < tiles[i].length; j++)
				{
					window.draw(tiles[i][j].getTileImage(), i * 50, j * 50);
				}
			}
			window.render();
	}
	
	public void setObserver(Observer o)
	{
		for(int i = 0; i < tiles.length; i++)
		{
			for(int j = 0; j < tiles[i].length; j++)
			{
				tiles[i][j].addObserver(o);
			}
		}
	}

}
