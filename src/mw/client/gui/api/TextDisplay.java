package mw.client.gui.api;

import org.minueto.MinuetoColor;
import org.minueto.image.MinuetoFont;
import org.minueto.image.MinuetoImage;
import org.minueto.image.MinuetoText;

public final class TextDisplay extends AbstractWindowComponent {

	public static final String DEFAULT_FONT_NAME = MinuetoFont.Monospaced;
	public static final int DEFAULT_FONT_SIZE = 15;
	public static final MinuetoFont DEFAULT_FONT = new MinuetoFont(DEFAULT_FONT_NAME, DEFAULT_FONT_SIZE, false,false);
	public static final MinuetoColor DEFAULT_TEXT_COLOR = MinuetoColor.BLACK;
	public static final MinuetoColor BORDER_COLOR = MinuetoColor.BLACK;
	
	private MinuetoText label;
	
	/* ========================
	 * 		Constructors
	 * ========================
	 */

	public TextDisplay(int x, int y, String text)
	{
		super(x, y, 0, 0);
		label = new MinuetoText(text, DEFAULT_FONT, DEFAULT_TEXT_COLOR);
		area.setHeight(label.getHeight());
		area.setWidth(label.getWidth());
	}
	
	/* ==========================
	 * 		Public methods
	 * ==========================
	 */


	/* ==========================
	 * 		Inherited methods
	 * ==========================
	 */


	@Override
	public MinuetoImage getImage() {
		MinuetoImage img = new MinuetoImage(area.getWidth(),area.getHeight());
		img.draw(label, 0, 0);
		ExtendedMinuetoImage.drawBorder(img);
		return img;
	}
	
	/*public void drawOn(MinuetoDrawingSurface canvas)
	{
		canvas.draw(label, coordX, coordY);
		canvas.drawLine(DEFAULT_COLOR, coordX, coordY+height, coordX+width, coordY+height);
		System.out.println("width="+width+", height="+height);
	}*/

}