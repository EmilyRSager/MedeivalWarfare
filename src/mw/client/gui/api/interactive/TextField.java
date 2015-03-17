package mw.client.gui.api.interactive;

import org.minueto.MinuetoColor;
import org.minueto.image.MinuetoImage;

import mw.client.gui.api.basics.AbstractWindowComponent;
import mw.client.gui.api.components.TextDisplay;
import mw.client.gui.window.GameWindow;

public class TextField extends AbstractWindowComponent 
						implements Clickeable {

	private static final MinuetoColor BORDER_COLOR = MinuetoColor.BLACK;
	
	private MouseClickHandler clickHandler;
	private boolean selected;
	
	
	/* ========================
	 * 		Constructors
	 * ========================
	 */

	public TextField(int x, int y, int width)
	{
		super(x, y, width, TextDisplay.DEFAULT_FONT_SIZE);
		clickHandler = new MouseClickHandler(area, this);
		selected = false;
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
		
	}

	/* ==========================
	 * 		Private methods
	 * ==========================
	 */


	/* ==========================
	 * 		Inherited methods
	 * ==========================
	 */


	@Override
	public MinuetoImage getImage()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void handleMouseClick(int x, int y, int button)
	{
		selected = true;
		// TODO find where to place the caret
	}


	/* ========================
	 * 		Static methods
	 * ========================
	 */

}