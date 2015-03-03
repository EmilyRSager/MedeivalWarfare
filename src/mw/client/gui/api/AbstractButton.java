package mw.client.gui.api;

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

	public static final MinuetoColor DEFAULT_BORDER_COLOR = ExtendedMinuetoColor.DARK_GREY;
	
	private static final int NORMAL_BORDER_THICKNESS = 1;
	private static final int SELECTED_BORDER_THICKNESS = 2;
	private static final int BORDER_MARGIN =1; 
	private static final int TOTAL_MARGIN = SELECTED_BORDER_THICKNESS+BORDER_MARGIN;
	
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
		
		area.setWidth(textImage.getWidth()+2*TOTAL_MARGIN);
		area.setHeight(textImage.getHeight()+2*TOTAL_MARGIN);
		

		MinuetoImage basicImage = new MinuetoImage(getWidth(),getHeight());
		basicImage.draw(textImage, TOTAL_MARGIN, TOTAL_MARGIN);
		normalImage = ExtendedMinuetoImage.drawBorder(basicImage, DEFAULT_BORDER_COLOR, NORMAL_BORDER_THICKNESS);
		/*normalImage.setPixel(NORMAL_BORDER_THICKNESS, NORMAL_BORDER_THICKNESS, DEFAULT_BORDER_COLOR);
		normalImage.setPixel(getWidth()-NORMAL_BORDER_THICKNESS, NORMAL_BORDER_THICKNESS, DEFAULT_BORDER_COLOR);
		normalImage.setPixel(getWidth()-NORMAL_BORDER_THICKNESS, getHeight()-NORMAL_BORDER_THICKNESS, DEFAULT_BORDER_COLOR);
		normalImage.setPixel(NORMAL_BORDER_THICKNESS, getHeight()-NORMAL_BORDER_THICKNESS, DEFAULT_BORDER_COLOR);*/
		selectedImage = ExtendedMinuetoImage.drawBorder(basicImage, DEFAULT_BORDER_COLOR, SELECTED_BORDER_THICKNESS);
		
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