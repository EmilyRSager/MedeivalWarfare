package mw.client.gui;
import org.minueto.*; 
import org.minueto.handlers.*; 
import org.minueto.image.*; 
import org.minueto.window.*; 

/**
 * The GameMap class contains all the functions required to visually represent the map in a game of Medieval Warfare.
 * @author Arthur Denefle0
 *
 */
public class GameMap 
{
	/**
	 * The method renderMap's main functionality is to construct the map image by drawing the tiles on the window. 
	 * @param tiles a double array of Tile objects to be displayed on the map.
	 */
	public static void renderMap(Tile[][] tiles)
	{
		MinuetoWindow window = new MinuetoFrame(500, 500, true);
		int wScreen = window.getWidth();
		int hScreen = window.getHeight();
		//assuming a full rectangular grid, the length of the arrays within the tile array will all be of the same length
		
		int numTiles = tiles.length * tiles[0].length;
		int tileWidth = wScreen / numTiles;
		int tileHeight = hScreen / numTiles;
		window.setVisible(true);
		while(true)
		{
			for(int i = 0; i < tiles.length; i++)
			{
				for(int j = 0; j < tiles.length; j++)
				{
					window.draw(tiles[i][j].getTileImage(), i * 50, j * 50);
				}
			}
			window.render();
			Thread.yield();
		}
	}
	
	public static void main(String[] args)
	{
		Tile[][] tiles = new Tile[10][10];
		for(int i = 0; i < tiles.length; i++)
		{
			for(int j = 0; j < tiles.length; j++)
			{
				tiles[i][j] = new Tile();
			}
		}
		tiles[5][5].update(0);
		renderMap(tiles);
	}

}
