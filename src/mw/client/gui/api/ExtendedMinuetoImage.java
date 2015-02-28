package mw.client.gui.api;

import java.awt.image.BufferedImage;

import org.minueto.MinuetoColor;
import org.minueto.image.MinuetoImage;

public final class ExtendedMinuetoImage /*extends MinuetoImage */{


	public static final MinuetoColor DEFAULT_BORDER_COLOR = MinuetoColor.BLACK;
	
	/* ========================
	 * 		Constructors
	 * ========================
	 */
	

	/*public ExtendedMinuetoImage(BufferedImage image) {
		super(image);
	}

	public ExtendedMinuetoImage(int x, int y) {
		super(x, y);
	}

	public ExtendedMinuetoImage(MinuetoImage img)
	{
		this(img.getWidth(), img.getHeight());
		for (int i=0;i<getWidth(); i++)
		{
			for (int j=0;j<getHeight();j++) {
				this.setPixel(i, j, img.getPixel(i, j));
			}
		}
	}*/

	/* ==========================
	 * 		Public methods
	 * ==========================
	 */

	/*public void drawBorder(MinuetoColor color)
	{
		final int width = getWidth();
		final int height = getHeight();
		this.drawLine(color, 0, 0, 0, height-1);
		this.drawLine(color, 0, height-1, width-1, height-1);
		this.drawLine(color, width-1, 0, width-1, height-1);
		this.drawLine(color, 0, 0, width-1, 0);
	}
	
	public void drawBorder(MinuetoColor color, int lineThickness)
	{
		final int width = getWidth();
		final int height = getHeight();
		for (int i=0;i<lineThickness;i++)
		{
			this.drawLine(color, i, i, i, height-1-i);
			this.drawLine(color, i, height-1-i, width-1-i, height-1-i);
			this.drawLine(color, width-1-i, i, width-1-i, height-1-i);
			this.drawLine(color, i, i, width-1-i, i);
		}
	}*/

	/* ==========================
	 * 		Private methods
	 * ==========================
	 */



	/* ========================
	 * 		Static methods
	 * ========================
	 */

	public static MinuetoImage drawBorder(MinuetoImage img)
	{
		return drawBorder(img,DEFAULT_BORDER_COLOR);
	}
	
	public static MinuetoImage drawBorder(MinuetoImage img, MinuetoColor color)
	{
		/*ExtendedMinuetoImage newImg = new ExtendedMinuetoImage(img);
		newImg.drawBorder(color);
		return newImg;*/
		return drawBorder(img, color, 1);
	}
	
	public static MinuetoImage drawBorder(MinuetoImage img, MinuetoColor color, int lineThickness)
	{
		MinuetoImage newImg = (MinuetoImage) img.clone();
		
		final int width = newImg.getWidth();
		final int height = newImg.getHeight();
		for (int i=0;i<lineThickness;i++)
		{
			newImg.drawLine(color, i, i, i, height-1-i);
			newImg.drawLine(color, i, height-1-i, width-1-i, height-1-i);
			newImg.drawLine(color, width-1-i, i, width-1-i, height-1-i);
			newImg.drawLine(color, i, i, width-1-i, i);
		}
		return newImg;
	}
	
	public static MinuetoImage coloredSquare(int width, int height, MinuetoColor c)
	{
		MinuetoImage img = new MinuetoImage(width, height);
		for(int i = 0; i < width; i++)
		{
			for(int j = 0; j < height; j++)
			{
				img.setPixel(i, j, c);
			}
		}
		return img;
	}
	
}