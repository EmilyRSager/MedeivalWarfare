package mw.client.gui;
import java.util.Observer;
import java.lang.Math;

import mw.client.controller.ActionInterpreter;
import mw.client.controller.ModelViewMapping;
import mw.client.gui.api.Clickeable;
import mw.client.gui.api.Displayable;
import mw.client.model.ModelTile;
import mw.shared.SharedColor;

import org.minueto.MinuetoColor;
import org.minueto.image.MinuetoImage;
import org.minueto.window.*; 


/**
 * The GameMap class contains all the functions required to visually represent the map in a game of Medieval Warfare.
 * @author Arthur Denefle
 *
 */
public class MapDisplay implements Displayable, Clickeable
{
	
	/*
	 * [0,0] [0,1] [0,2]
	 * [1,0] [1,1] [1,2]
	 * [2,0] [2,1] [2,2]
	 * 
	 */
	
	private ImageTile[][] tiles;
	private int tileWidth;
	private int tileHeight;
	
	
	/* ========================
	 * 		Constructors
	 * ========================
	 */
	

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
		tileWidth = tiles[0][0].getTileImage().getWidth();
		tileHeight = tiles[0][0].getTileImage().getHeight();
	}
	
	public MapDisplay(ImageTile[][] givenTiles)
	{
		tiles = givenTiles;
		tileWidth = tiles[0][0].getTileImage().getWidth();
		tileHeight = tiles[0][0].getTileImage().getHeight();
	}

	
	/* ==========================
	 * 		Public methods
	 * ==========================
	 */

	
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
		return this.tileWidth * this.tiles.length;
	}
	
	public int getHeight()
	{
		return this.tileHeight * this.tiles[0].length + tileHeight / 2;
	}

	public ImageTile getClickedTile(int x, int y)
	{
		int xIndex = (int) x / tileWidth;
		int yIndex;
		if(xIndex % 2 == 0)
		{
			yIndex = (int) y / tileHeight;
		}
		else
		{
			yIndex = (int) Math.floor(((y - (tileHeight / 2)) / (double) tileHeight)); 
		}
		/*if(xIndex % 2 != 0 && y < tileHeight / 2)
		{
			return null;
		}*/
		//else if(xIndex % 2 == 0 && y > tileHeight() )
		try
		{
			return this.tiles[xIndex][yIndex];
		}
		catch (ArrayIndexOutOfBoundsException e)
		{
			return null;
		}
	}
	
	public void setWindow(GameWindow window)
	{
		setObserver(window);
	}
	

	public int getTileHeight() {
		return tileHeight;
	}
	
	/* ==========================
	 * 		Inherited methods
	 * ==========================
	 */

	
	@Override
	public MinuetoImage getImage()
	{
		MinuetoImage newImage = new MinuetoImage(this.getWidth(), this.getHeight());
		for(int i = 0; i < tiles.length; i++)
		{
			for(int j = 0; j < tiles[i].length; j++)
			{
				if(i % 2 == 0)
				{
					newImage.draw(tiles[i][j].getTileImage(), i * ImageTile.DEFAULT_TILE_WIDTH, j * ImageTile.DEFAULT_TILE_HEIGHT);
				}
				else
				{
					newImage.draw(tiles[i][j].getTileImage(), i * ImageTile.DEFAULT_TILE_WIDTH, (j * ImageTile.DEFAULT_TILE_HEIGHT) + (int)(.5 * ImageTile.DEFAULT_TILE_HEIGHT));
				}
			}
		}
		return newImage;
	}
	
	@Override
	public void handleMouseClick(int x, int y, int button)
	{
		ImageTile clickedTile = this.getClickedTile(x, y);
		ModelTile clickedModelTile = ModelViewMapping.singleton().getModelTile(clickedTile);
		if(clickedModelTile != null)
		{
			System.out.println(button);
			if (button == 1)
				ActionInterpreter.singleton().primarySelect(clickedTile);
			/*clickedModelTile.setColor(SharedColor.RED);
			clickedModelTile.notifyObservers();*/
		}
	}


	/* ========================
	 * 		Static methods
	 * ========================
	 */
	
	
}
