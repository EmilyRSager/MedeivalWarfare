package mw.client.gui.api.components;

import mw.client.gui.api.basics.AbstractWindowComponent;
import mw.client.gui.api.extminueto.ExtendedMinuetoColor;
import mw.client.gui.api.extminueto.ExtendedMinuetoImage;

import org.minueto.MinuetoColor;
import org.minueto.image.MinuetoFont;
import org.minueto.image.MinuetoImage;
import org.minueto.image.MinuetoText;

/**
 * The TextDisplay class gives a basic WindowComponent for displaying text on the screen.
 * @author Hugo Kapp
 *
 */
public final class TextDisplay extends AbstractWindowComponent {

	public static final String DEFAULT_FONT_NAME = MinuetoFont.Dialog;
	public static final int DEFAULT_FONT_SIZE = 16;
	public static final MinuetoFont DEFAULT_FONT = new MinuetoFont(DEFAULT_FONT_NAME, DEFAULT_FONT_SIZE, false,false);
	public static final MinuetoColor DEFAULT_TEXT_COLOR = MinuetoColor.BLACK;
	
	private static final MinuetoColor BORDER_COLOR = ExtendedMinuetoColor.LIGHT_GREY;
	private static final MinuetoColor DEFAULT_BACKGROUND_COLOR = MinuetoColor.WHITE;
	
	private static final int X_BORDER_MARGIN = 8;
	private static final int Y_BORDER_MARGIN = 2;
	
	private MinuetoText label;
	private MinuetoImage image;
	
	private final MinuetoColor backgroundColor;
	
	/* ========================
	 * 		Constructors
	 * ========================
	 */

	public TextDisplay(int x, int y, String text, MinuetoColor backgroundColor)
	{
		super(x, y, 0, 0);
		label = new MinuetoText(text, DEFAULT_FONT, DEFAULT_TEXT_COLOR);
		area.setHeight(label.getHeight()+2*Y_BORDER_MARGIN);
		area.setWidth(label.getWidth()+2*X_BORDER_MARGIN);
		this.backgroundColor = backgroundColor;
		image = buildImage(label);
	}
	
	/**
	 * Creates a new TextDisplay with a position and a text.
	 * @param x the x coordinate of the new TextDisplay
	 * @param y the y coordinate of the new TextDisplay
	 * @param text the text of the new TextDisplay
	 */
	public TextDisplay(int x, int y, String text)
	{
		this(x,y,text,DEFAULT_BACKGROUND_COLOR);
	}
	
	/**
	 * Creates a new TextDisplay with just a text. This constructor should only be called when the TextDisplay is going
	 * to be used in a Layout.
	 * @param text the text of the new TextDisplay
	 */
	public TextDisplay(String text)
	{
		this(0,0,text);
	}
	
	public TextDisplay(String text, MinuetoColor backgroundColor)
	{
		this(0,0,text,backgroundColor);
	}
	
	/* ==========================
	 * 		Public methods
	 * ==========================
	 */

	


	/* ==========================
	 * 		Private methods
	 * ==========================
	 */
	
	
	private MinuetoImage buildImage(MinuetoText label)
	{
		MinuetoImage img = ExtendedMinuetoImage.coloredSquare(getWidth(), getHeight(), backgroundColor);//new MinuetoImage(area.getWidth(),area.getHeight());
		img.draw(label, X_BORDER_MARGIN, Y_BORDER_MARGIN);
		img = ExtendedMinuetoImage.drawBorder(img, BORDER_COLOR);
		return img;
	}
	

	/* ==========================
	 * 		Inherited methods
	 * ==========================
	 */


	@Override
	public MinuetoImage getImage() {
		return image;
	}
	
	/*public void drawOn(MinuetoDrawingSurface canvas)
	{
		canvas.draw(label, coordX, coordY);
		canvas.drawLine(DEFAULT_COLOR, coordX, coordY+height, coordX+width, coordY+height);
		System.out.println("width="+width+", height="+height);
	}*/
	


}