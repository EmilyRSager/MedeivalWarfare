package mw.client.gui.api.interactive;

import org.minueto.MinuetoColor;
import org.minueto.handlers.MinuetoMouse;
import org.minueto.handlers.MinuetoMouseHandler;
import org.minueto.image.MinuetoImage;
import org.minueto.image.MinuetoText;

import mw.client.gui.api.basics.AbstractWindowComponent;
import mw.client.gui.api.components.TextDisplay;
import mw.client.gui.api.extminueto.ExtendedMinuetoImage;
import mw.client.gui.window.GameWindow;

public class TextField extends AbstractWindowComponent 
						implements MinuetoMouseHandler {

	private static final MinuetoColor BORDER_COLOR = MinuetoColor.BLACK;
	private static final int X_MARGIN = 2;
	private static final int Y_MARGIN = 2;
	
	private GameWindow window;
	
	private MinuetoImage image;
	String text;
	
	private Caret caret;
	//private MouseClickHandler clickHandler;
	private boolean active;
	
	
	/* ========================
	 * 		Constructors
	 * ========================
	 */

	public TextField(int x, int y, int width)
	{
		super(x, y, width + 2*X_MARGIN, TextDisplay.DEFAULT_FONT_SIZE + 2*Y_MARGIN);
		//clickHandler = new MouseClickHandler(area, this);
		caret = new Caret(this);
		active = false;
		text = "";
		buildImage();
	}

	public TextField(int width)
	{
		this(0,0,width);
	}
	
	
	/* ==========================
	 * 		Public methods
	 * ==========================
	 */

	
	public void setWindow(GameWindow window)
	{
		this.window = window;
		window.registerMouseHandler(this);
		//window.registerMouseHandler(clickHandler);
	}

	public void setText(String str)
	{
		text = str;
		buildImage();
		setChanged(ChangedState.IMAGE);
		notifyObservers();
	}
	
	public void insertChar(char c, int pos)
	{
		setText(text.substring(0, pos) + c + text.substring(pos));
	}
	
	public String getText() {
		return text;
	}
	
	public void enable()
	{
		window.registerKeyboardHandler(caret);
		active = true;
	}
	
	public void disable()
	{
		window.unregisterKeyboardHandler(caret);
		active = false;
	}
	
	
	/* ==========================
	 * 		Private methods
	 * ==========================
	 */

	private void buildImage()
	{
		image = ExtendedMinuetoImage.coloredSquare(getWidth(), getHeight(), MinuetoColor.WHITE);
		MinuetoText mintext = new MinuetoText(text, TextDisplay.DEFAULT_FONT, TextDisplay.DEFAULT_TEXT_COLOR);
		image.draw(mintext, X_MARGIN, Y_MARGIN);//ExtendedMinuetoImage.drawInTheMiddleOf(image, mintext);
		//image.draw(mintext, 0, 0);
		//image = ExtendedMinuetoImage.addMargin(image, X_MARGIN, Y_MARGIN, MinuetoColor.WHITE);
		image = ExtendedMinuetoImage.drawBorder(image, BORDER_COLOR);
		//System.out.println("Comp width : "+getWidth());
		//System.out.println("Image width : "+image.getWidth());
	}
	

	/* ==========================
	 * 		Inherited methods
	 * ==========================
	 */


	@Override
	public MinuetoImage getImage()
	{
		return image;
	}

	@Override
	public void handleMouseMove(int arg0, int arg1)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handleMousePress(int arg0, int arg1, int arg2)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handleMouseRelease(int arg0, int arg1, int arg2)
	{
		boolean clickIn = area.containsPoint(arg0, arg1);
		if (!clickIn && active)
		{
			System.out.println("That click disabled");
			disable();
		}
		else if (clickIn && !active)
		{
			System.out.println("That click enabled");
			enable();
		}
	}


	/* ========================
	 * 		Static methods
	 * ========================
	 */

}