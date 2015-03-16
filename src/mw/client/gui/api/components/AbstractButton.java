package mw.client.gui.api.components;

import mw.client.gui.api.basics.AbstractWindowComponent;
import mw.client.gui.api.extminueto.ExtendedMinuetoColor;
import mw.client.gui.api.extminueto.ExtendedMinuetoImage;

import org.minueto.MinuetoColor;
import org.minueto.handlers.MinuetoMouseHandler;
import org.minueto.image.MinuetoImage;
import org.minueto.image.MinuetoText;

/**
 * The AbstractButton class provides an easy way to implement buttons. Whenever a click occurs, this class 
 * will call the abstract method buttonClick(button), which defines what needs to be done when the button is
 * clicked. In order for the buttons to be displayed correctly, the AbstractButton must be observed.
 * @author Hugo
 *
 */
public abstract class AbstractButton extends AbstractWindowComponent implements MinuetoMouseHandler {

	//public static final MinuetoColor DEFAULT_BORDER_COLOR = ExtendedMinuetoColor.DARK_GREY;
	
	private static final int BORDER_THICKNESS = 1;
	//private static final int SELECTED_BORDER_THICKNESS = 2;
	private static final int X_BORDER_MARGIN = 5; 
	private static final int Y_BORDER_MARGIN = 2;
	private static final int TOTAL_X_MARGIN = BORDER_THICKNESS + X_BORDER_MARGIN;
	private static final int TOTAL_Y_MARGIN = BORDER_THICKNESS + Y_BORDER_MARGIN;
	
	private static final MinuetoColor TOP_COLOR = ExtendedMinuetoColor.mixColors(MinuetoColor.BLACK, MinuetoColor.WHITE, 0.05);
	private static final MinuetoColor BOT_COLOR = ExtendedMinuetoColor.mixColors(MinuetoColor.BLACK, MinuetoColor.WHITE, 0.08);
	private static final MinuetoColor PRESSED_COLOR = ExtendedMinuetoColor.mixColors(MinuetoColor.BLACK, MinuetoColor.WHITE, 0.12);
	
	private MinuetoImage normalImage;
	private MinuetoImage selectedImage;
	private boolean clicking;
	
	
	/* ========================
	 * 		Constructors
	 * ========================
	 */
	
	/**
	 * Creates a new AbstractButton with a position and a text.
	 * @param x the x coordinate of the new AbstractButton
	 * @param y the y coordinate of the new AbstractButton
	 * @param text the text of the new AbstractButton
	 */
	public AbstractButton(int x, int y, String text) {
		super(x, y, 0, 0);
		
		MinuetoImage textImage = new MinuetoText(text, TextDisplay.DEFAULT_FONT, TextDisplay.DEFAULT_TEXT_COLOR);
		
		area.setWidth(textImage.getWidth()+2*TOTAL_X_MARGIN);
		area.setHeight(textImage.getHeight()+2*TOTAL_Y_MARGIN);
		

		MinuetoImage basicImage = new MinuetoImage(getWidth(),getHeight());
		basicImage.draw(textImage, TOTAL_X_MARGIN, TOTAL_Y_MARGIN);
		//normalImage = ExtendedMinuetoImage.drawBorder(basicImage, DEFAULT_BORDER_COLOR, NORMAL_BORDER_THICKNESS);
		//selectedImage = ExtendedMinuetoImage.drawBorder(basicImage, DEFAULT_BORDER_COLOR, SELECTED_BORDER_THICKNESS);
		
		MinuetoImage topHalf = ExtendedMinuetoImage.coloredSquare(area.getWidth(), area.getHeight()/2, TOP_COLOR);
		MinuetoImage botHalf = ExtendedMinuetoImage.coloredSquare(area.getWidth(), area.getHeight()/2, BOT_COLOR);
		
		normalImage = new MinuetoImage(getWidth(), getHeight());
		normalImage.draw(topHalf, 0, 0);
		normalImage.draw(botHalf, 0, area.getHeight()/2);
		normalImage.draw(basicImage, 0, 0);
		normalImage = ExtendedMinuetoImage.drawBorder(normalImage);
		
		selectedImage = ExtendedMinuetoImage.coloredSquare(area.getWidth(), area.getHeight(), PRESSED_COLOR);
		selectedImage.draw(basicImage, 0, 0);
		selectedImage = ExtendedMinuetoImage.drawBorder(selectedImage);
		
		clicking = false;
	}

	/**
	 * Creates a new AbstractButton with just a text. This constructor should only 
	 * be called when the AbstractButton is going to be used inside a layout.
	 * @param text
	 */
	public AbstractButton(String text)
	{
		this(0, 0, text);
	}

	
	/* ==========================
	 * 		Inherited methods
	 * ==========================
	 */

	
	@Override
	public MinuetoImage getImage() {
		if (clicking)
			return selectedImage;
		else
			return normalImage;
	}

	@Override
	public void handleMousePress(int x, int y, int button)
	{
		boolean b = area.containsPoint(x, y);
		if (b!=clicking) {
			clicking = b;
			setChanged();
			notifyObservers();
		}
	}
	
	@Override
	public void handleMouseRelease(int x, int y, int button)
	{
		if (clicking) {
			buttonClick(button);
			clicking=false;
			setChanged();
			notifyObservers();
		}
	}

	@Override
	public void handleMouseMove(int x, int y) {
		if (clicking && !area.containsPoint(x, y)) {
			clicking = false;
			setChanged();
			notifyObservers();
		}
	}
	
	/**
	 * Defines what needs to be done when this AbstractButton is clicked
	 * @param mouseButton the mouse button that clicked on the button
	 */
	public abstract void buttonClick(int mouseButton);
}