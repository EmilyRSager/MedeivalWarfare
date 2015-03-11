package mw.client.gui;

import java.util.Observable;

import org.minueto.MinuetoColor;
import org.minueto.image.MinuetoImage;

import mw.client.gui.api.Displayable;
import mw.client.gui.api.ExtendedMinuetoColor;
import mw.client.gui.api.ExtendedMinuetoImage;
import mw.client.model.ModelTile.*;

public class ImageTile extends Observable implements Displayable {
	
	public static final int DEFAULT_TILE_WIDTH = 70;
	public static final int DEFAULT_TILE_HEIGHT = 70;
	public static final Hexagon DEFAULT_HEXAGON = Hexagon.getHexagon(DEFAULT_TILE_WIDTH, DEFAULT_TILE_HEIGHT);
	
	private final Hexagon hex;
	private MinuetoImage image;
	

	/* ========================
	 * 		Constructors
	 * ========================
	 */

	public ImageTile() {
		this(DEFAULT_HEXAGON);
	}

	public ImageTile(int width, int height) {
		this(Hexagon.getHexagon(width, height));
	}
	
	public ImageTile(Hexagon hex) {
		this.hex = hex;
		setImage(new MinuetoImage(hex.getWidth(), hex.getHeight()));
	}
	
	/* ========================
	 * 		Getter methods
	 * ========================
	 */
	
	public Hexagon getHexagon()
	{
		return hex;
	}
	/* ==========================
	 * 		Public methods
	 * ==========================
	 */
	
	public void updateImage(MinuetoImage newImage)
	{
		setImage(newImage);
		setChanged();
		notifyObservers();
	}
	
	public void updateColor(MinuetoColor c)
	{
		setImage(ExtendedMinuetoImage.coloredHexagon(hex, c));
		setChanged();
		notifyObservers();
	}
	
	private void setImage(MinuetoImage newImage)
	{
		image = newImage;
		drawBorder(ExtendedMinuetoColor.GREY);
	}

	public void drawBorder(MinuetoColor c)
	{
		image = ExtendedMinuetoImage.drawHexBorder(image, c, hex);
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