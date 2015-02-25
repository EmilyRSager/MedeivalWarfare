package mw.client.gui;

import java.util.Observable;

import mw.client.gui.ImageFileManager.TileType;

import org.minueto.image.*;

/**
 * This class defines the Tile object 
 * @author Arthur Denefle
 *
 */
public class ImageTile extends Observable {
	private MinuetoImage image;
	
	public ImageTile()
	{
		image = ImageFileManager.getTileImage(TileType.DEFAULT);
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
}
