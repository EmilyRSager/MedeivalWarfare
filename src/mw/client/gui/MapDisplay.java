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
public class MapDisplay 
{
	private ImageTile[][] tiles;
	
	public MapDisplay()
	{
		tiles = new ImageTile[10][10];
		for(int i = 0; i < tiles.length; i++)
		{
			for(int j = 0; j < tiles.length; j++)
			{
				tiles[i][j] = new ImageTile();
			}
		}
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
				for(int j = 0; j < tiles.length; j++)
				{
					window.draw(tiles[i][j].getTileImage(), i * 50, j * 50);
				}
			}
			System.out.println("one");
			window.render();
			System.out.println("two");
			//Thread.yield();
	}

}
