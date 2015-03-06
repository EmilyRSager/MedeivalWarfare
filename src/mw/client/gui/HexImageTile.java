package mw.client.gui;

import java.util.Observable;

import org.minueto.MinuetoColor;
import org.minueto.image.MinuetoImage;

import mw.client.gui.ImageFileManager.TileType;
import mw.client.gui.api.Displayable;
import mw.client.gui.api.ExtendedMinuetoColor;
import mw.client.gui.api.ExtendedMinuetoImage;

public class HexImageTile extends Observable implements Displayable {
	
	public static final int DEFAULT_TILE_WIDTH = 50;
	public static final int DEFAULT_TILE_HEIGHT = 50;
	
	private final Hexagon hex;
	private MinuetoImage image;
	

	/* ========================
	 * 		Constructors
	 * ========================
	 */

	public HexImageTile()
	{
		this(DEFAULT_TILE_WIDTH, DEFAULT_TILE_HEIGHT);
	}

	public HexImageTile(int width, int height) {
		this(new Hexagon(width, height));
	}
	
	public HexImageTile(Hexagon hex) {
		this.hex = hex;
		setImage(new MinuetoImage(hex.getWidth(), hex.getHeight()));
	}
	
	/* ==========================
	 * 		Public methods
	 * ==========================
	 */
	
	public void update()
	{
		setImage(ImageFileManager.getTileImage(TileType.GRASS));
		setChanged();
		notifyObservers();
	}
	
	public void updateColor(MinuetoColor c)
	{
		setImage(ExtendedMinuetoImage.coloredHexagon(hex, c));
		setChanged();
		notifyObservers();
	}
	
	public void setImage(MinuetoImage newImage)
	{
		image = ExtendedMinuetoImage.drawHexBorder(newImage, ExtendedMinuetoColor.GREY, hex);
	}

	/* ==========================
	 * 		Private methods
	 * ==========================
	 */


	/* ==========================
	 * 		Inherited methods
	 * ==========================
	 */

	@Override
	public MinuetoImage getImage()
	{
		return image;
	}


	/* ========================
	 * 		Static methods
	 * ========================
	 */

}