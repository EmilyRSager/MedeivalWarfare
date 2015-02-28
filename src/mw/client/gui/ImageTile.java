package mw.client.gui;

import java.util.Observable;

import mw.client.gui.ImageFileManager.TileType;
import mw.client.gui.api.ExtendedMinuetoColor;
import mw.client.gui.api.ExtendedMinuetoImage;

import org.minueto.MinuetoColor;
import org.minueto.image.*;

/**
 * This class defines the ImageTile object 
 * @author Arthur Denefle
 *
 */
public class ImageTile extends Observable {
	private MinuetoImage image;
	public static final int DEFAULT_TILE_WIDTH = 50;
	public static final int DEFAULT_TILE_HEIGHT = 50;
	
	/**
	 * Default ImageTile constructor, creates an ImageTile with a blank MinuetoImage.
	 */
	public ImageTile()
	{
		//image = ImageFileManager.getTileImage(TileType.DEFAULT);
		setImage(new MinuetoImage(DEFAULT_TILE_WIDTH, DEFAULT_TILE_HEIGHT));
	}
	/**
	 * Getter for an ImageTile's MinuetoImage.
	 * @return MinuetoImage
	 */
	public MinuetoImage getTileImage()
	{
		return image;
	}
	/**
	 * 
	 */
	public void update()
	{
		setImage(ImageFileManager.getTileImage(TileType.GRASS));
		setChanged();
		notifyObservers();
	}
	
	public void updateColor(MinuetoColor c)
	{
		for(int i = 0; i < DEFAULT_TILE_WIDTH; i++)
		{
			for(int j = 0; j < DEFAULT_TILE_HEIGHT; j++)
			{
				this.image.setPixel(i, j, c);
			}
		}
		setImage(image);
		setChanged();
		notifyObservers();
	}
	
	public void setImage(MinuetoImage newImage)
	{
		image = newImage;
		ExtendedMinuetoImage.drawBorder(newImage, ExtendedMinuetoColor.GREY);
	}
}
