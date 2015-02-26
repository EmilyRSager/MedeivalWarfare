package mw.client.gui;

import java.util.Observable;

import mw.client.gui.ImageFileManager.TileType;

import org.minueto.MinuetoColor;
import org.minueto.image.*;

/**
 * This class defines the Tile object 
 * @author Arthur Denefle
 *
 */
public class ImageTile extends Observable {
	private MinuetoImage image;
	public static final int DEFAULT_TILE_WIDTH = 50;
	public static final int DEFAULT_TILE_HEIGHT = 50;
	
	public ImageTile()
	{
		//image = ImageFileManager.getTileImage(TileType.DEFAULT);
		image = new MinuetoImage(DEFAULT_TILE_WIDTH, DEFAULT_TILE_HEIGHT);
	}
	
	public MinuetoImage getTileImage()
	{
		return this.image;
	}
	
	public void update()
	{
		this.image = ImageFileManager.getTileImage(TileType.GRASS);
		setChanged();
		notifyObservers();
	}
	
	public void updateColor(MinuetoColor c)
	{
		for(int i = 0; i < 49; i++)
		{
			for(int j = 0; j < 49; j++)
			{
				this.image.setPixel(i, j, c);
			}
		}
		
		setChanged();
		notifyObservers();
	}
}
