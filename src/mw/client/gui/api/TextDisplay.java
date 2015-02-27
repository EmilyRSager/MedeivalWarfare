package mw.client.gui.api;

import org.minueto.MinuetoColor;
import org.minueto.image.MinuetoDrawingSurface;
import org.minueto.image.MinuetoFont;
import org.minueto.image.MinuetoImage;
import org.minueto.image.MinuetoText;

public final class TextDisplay extends WindowComponent {

	public static final String DEFAULT_FONT_NAME = MinuetoFont.Monospaced;
	public static final int DEFAULT_FONT_SIZE = 10;
	public static final MinuetoFont DEFAULT_FONT = new MinuetoFont(DEFAULT_FONT_NAME, DEFAULT_FONT_SIZE, false,false);
	
	private MinuetoText label;
	
	/* ========================
	 * 		Constructors
	 * ========================
	 */

	public TextDisplay(int x, int y, int width, int height, String text)
	{
		super(x, y, width, height);
		label = new MinuetoText(text, DEFAULT_FONT, MinuetoColor.BLACK);
	}

	@Override
	public MinuetoImage getImage() {
		MinuetoImage img = new MinuetoImage(width,height);
		label.draw(img, 0, 0);
		return img;
	}

	@Override
	public void handleMouseClick(int x, int y, int button) {
		
	}
	
	/* ==========================
	 * 		Public methods
	 * ==========================
	 */


	/* ==========================
	 * 		Private methods
	 * ==========================
	 */


}