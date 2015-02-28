package mw.client.gui.api;

import org.minueto.MinuetoColor;
import org.minueto.image.MinuetoImage;

public final class ExtendedMinuetoImage /*extends MinuetoImage*/ {


	public static final MinuetoColor DEFAULT_BORDER_COLOR = MinuetoColor.BLACK;
	
	/* ========================
	 * 		Constructors
	 * ========================
	 */
	
	/*public ExtendedMinuetoImage() {
		// TODO Auto-generated constructor stub
	}

	public ExtendedMinuetoImage(BufferedImage image) {
		super(image);
		// TODO Auto-generated constructor stub
	}

	public ExtendedMinuetoImage(int x, int y) {
		super(x, y);
		// TODO Auto-generated constructor stub
	}*/



	/* ==========================
	 * 		Public methods
	 * ==========================
	 */


	/* ==========================
	 * 		Private methods
	 * ==========================
	 */



	/* ========================
	 * 		Static methods
	 * ========================
	 */

	public static void drawBorder(MinuetoImage img)
	{
		drawBorder(img,DEFAULT_BORDER_COLOR);
	}
	
	public static void drawBorder(MinuetoImage img, MinuetoColor color)
	{
		final int width = img.getWidth();
		final int height = img.getHeight();
		img.drawLine(color, 0, 0, 0, height-1);
		img.drawLine(color, 0, height-1, width-1, height-1);
		img.drawLine(color, width-1, 0, width-1, height-1);
		img.drawLine(color, 0, 0, width-1, 0);
	}
	
}