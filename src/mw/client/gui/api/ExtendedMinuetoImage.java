package mw.client.gui.api;

import mw.client.gui.Hexagon;
import mw.client.gui.Hexagon.RelativePosition;

import org.minueto.MinuetoColor;
import org.minueto.image.MinuetoImage;

public final class ExtendedMinuetoImage /*extends MinuetoImage */{


	public static final MinuetoColor DEFAULT_BORDER_COLOR = MinuetoColor.BLACK;


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
	
	public static MinuetoImage coloredHexagon(int width, int height, MinuetoColor color) {
		return coloredHexagon(new Hexagon(width,height), color);
	}
	
	public static MinuetoImage coloredHexagon(Hexagon hex, MinuetoColor color)
	{
		final int width = hex.getWidth();
		final int height = hex.getHeight();
		MinuetoImage img = new MinuetoImage(width,height);
		for (int i=0; i<width; i++)
		{
			for (int j=0;j<height;j++)
			{
				if (hex.locatePoint(i,j)==RelativePosition.CENTER)
					img.setPixel(i, j, color);
			}
		}
		return img;
	}
	
}