package mw.client.gui;
import java.util.Observer;
import java.awt.Color;

import org.minueto.MinuetoColor;
import org.minueto.window.*; 

/**
 * The GameMap class contains all the functions required to visually represent the map in a game of Medieval Warfare.
 * @author Arthur Denefle
 *
 */
public class MapDisplay 
{
	//public static final MinuetoColor color = new MinuetoColor(2);
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
		MinuetoColor color =  MinuetoColor.BLACK;
		tiles[2][6].updateColor(color);
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
					if(i % 2 == 0)
					{
						window.draw(tiles[i][j].getTileImage(), i * ImageTile.DEFAULT_TILE_WIDTH, j * ImageTile.DEFAULT_TILE_HEIGHT);
					}
					else
					{
						window.draw(tiles[i][j].getTileImage(), i * ImageTile.DEFAULT_TILE_WIDTH, (j * ImageTile.DEFAULT_TILE_HEIGHT) + (int)(.5 * ImageTile.DEFAULT_TILE_HEIGHT));
					}
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
	public int getWidth()
	{
		return this.tiles[0][0].getTileImage().getWidth() * this.tiles.length;
	}
	public int getHeight()
	{
		return this.tiles[0][0].getTileImage().getHeight() * this.tiles.length + tiles[0][0].getTileImage().getHeight() / 2;
	}

}
