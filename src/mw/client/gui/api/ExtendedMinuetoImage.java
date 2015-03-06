package mw.client.gui.api;

import java.util.List;

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
	
	public static MinuetoImage drawHexBorder(MinuetoImage img, MinuetoColor color, Hexagon hex)
	{
		MinuetoImage newImg = (MinuetoImage) img.clone();
		
		final List<Hexagon.Point> vertices = hex.getVertices();
		newImg.drawLine(color, vertices.get(0).x, vertices.get(0).y, vertices.get(1).x, vertices.get(1).y);
		newImg.drawLine(color, vertices.get(1).x, vertices.get(1).y, vertices.get(2).x, vertices.get(2).y);
		newImg.drawLine(color, vertices.get(2).x, vertices.get(2).y, vertices.get(3).x, vertices.get(3).y);
		newImg.drawLine(color, vertices.get(3).x, vertices.get(3).y, vertices.get(4).x, vertices.get(4).y);
		newImg.drawLine(color, vertices.get(4).x, vertices.get(4).y, vertices.get(5).x, vertices.get(5).y);
		newImg.drawLine(color, vertices.get(5).x, vertices.get(5).y, vertices.get(0).x, vertices.get(0).y);
		
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