package mw.client.gui.window;

import java.util.Observable;

import org.minueto.MinuetoColor;
import org.minueto.image.MinuetoImage;

import mw.client.gui.api.basics.Displayable;
import mw.client.gui.api.extminueto.ExtendedMinuetoColor;
import mw.client.gui.api.extminueto.ExtendedMinuetoImage;
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
		//System.out.println("Changing the image");
		setImage(newImage);
		//System.out.println("Changed the image");
		/*setChanged();
		notifyObservers();*/
	}
	
	/*public void updateColor(MinuetoColor c)
	{
		setImage(ExtendedMinuetoImage.coloredHexagon(hex, c));
		setChanged();
		notifyObservers();
	}*/
	
	private void setImage(MinuetoImage newImage)
	{
		image = newImage;
		drawBorder(ExtendedMinuetoColor.GREY);
	}

	public void drawBorder(MinuetoColor c)
	{
		drawBorder(c, 1);
	}

	public void drawBorder(MinuetoColor c, int thickness)
	{
		image = ExtendedMinuetoImage.drawHexBorder(image, c, hex, thickness);
		//System.out.println("Changing the border");
		setChanged();
		notifyObservers();
		//System.out.println("Changed the border");
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