package mw.client.gui.api;

import java.util.List;

import mw.client.gui.Hexagon;
import mw.client.gui.Hexagon.RelativePosition;

import org.minueto.MinuetoColor;
import org.minueto.image.MinuetoImage;

public final class ExtendedMinuetoImage /*extends MinuetoImage */{


	public static final MinuetoColor DEFAULT_BORDER_COLOR = MinuetoColor.BLACK;


	/* ===========================
	 * 		Basic image drawing
	 * ===========================
	 */

	
	public static MinuetoImage drawInTheMiddleOf(MinuetoImage bigImg, MinuetoImage smallImg)
	{
		final int xPos = (bigImg.getWidth() - smallImg.getWidth())/2;
		final int yPos = (bigImg.getHeight() - smallImg.getHeight())/2;
		if (xPos < 0 || yPos < 0)
			throw new IllegalArgumentException("The small image is too big to be drawn on the big image : "
															+smallImg.getWidth()+"x"+smallImg.getHeight()+" > "
															+bigImg.getWidth()+"x"+bigImg.getHeight());
		
		MinuetoImage resImg = (MinuetoImage)bigImg.clone();
		resImg.draw(smallImg, xPos, yPos);
		return resImg;
	}
	
	public static MinuetoImage cropToFitArea(MinuetoImage img, WindowArea area)
	{
		MinuetoImage retImg = (MinuetoImage) img.clone();
		if (retImg.getWidth() > area.getWidth()) {
			System.out.println("Cropping from width = "+retImg.getWidth()+" to "+area.getWidth());
			retImg = retImg.crop(0, 0, area.getWidth(), retImg.getHeight());
		}
		if (retImg.getHeight() > area.getHeight()) {
			System.out.println("Cropping from height = "+retImg.getHeight()+" to "+area.getHeight());
			retImg = retImg.crop(0, 0, retImg.getWidth(), area.getHeight());
		}
		return retImg;
	}
	
	
	/* ========================
	 * 		Borders
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
	
	
	/* ========================
	 * 		Colored images
	 * ========================
	 */
	
	
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
		return coloredHexagon(Hexagon.getHexagon(width,height), color);
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