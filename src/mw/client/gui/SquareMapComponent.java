package mw.client.gui;

import mw.client.gui.api.AbstractWindowComponent;
import mw.client.gui.api.Clickeable;
import mw.client.gui.api.MouseClickHandler;
import org.minueto.handlers.MinuetoKeyboard;
import org.minueto.handlers.MinuetoKeyboardHandler;
import org.minueto.image.MinuetoImage;

@Deprecated
public final class SquareMapComponent extends AbstractWindowComponent implements
		Clickeable, MinuetoKeyboardHandler {
	
	private static final int X_OFFSET_STEP = 10;
	private static final int Y_OFFSET_STEP = 10;
	
	private int xOffset, yOffset;
	private final int minXOffset, minYOffset;
	private final int maxXOffset, maxYOffset;
	private final SquareMapDisplay mapDisp;
	private final MouseClickHandler clickHandler;

	/* ========================
	 * 		Constructors
	 * ========================
	 */
	
	public SquareMapComponent(int x, int y, int width, int height, SquareMapDisplay mapDisp) {
		super(x, y, width, height);
		this.mapDisp = mapDisp;
		clickHandler = new MouseClickHandler(area, this);
		
		minXOffset=0;
		minYOffset=0;
		maxXOffset = Math.max(mapDisp.getWidth()-width, 0);
		maxYOffset = Math.max(mapDisp.getHeight()-height, 0);
		
		xOffset = minXOffset;
		yOffset = minYOffset;
		
	}


	/* ==========================
	 * 		Public methods
	 * ==========================
	 */
	
	public void setWindow(GameWindow window)
	{
		mapDisp.setWindow(window);
		window.registerMouseHandler(clickHandler);
		window.registerKeyboardHandler(this);
		this.addObserver(window);
	}
	
	/* ==========================
	 * 		Inherited methods
	 * ==========================
	 */
	

	@Override
	public void handleKeyPress(int arg0) {
		final int oldYOffset = yOffset;
		final int oldXOffset = xOffset;
		switch(arg0)
		{
		case MinuetoKeyboard.KEY_DOWN:
			yOffset+=Y_OFFSET_STEP;
			yOffset = Math.min(yOffset, maxYOffset);
			break;
			
		case MinuetoKeyboard.KEY_UP:
			yOffset-=Y_OFFSET_STEP;
			yOffset = Math.max(yOffset, minYOffset);
			break;
			
		case MinuetoKeyboard.KEY_LEFT:
			xOffset-=X_OFFSET_STEP;
			xOffset = Math.max(xOffset, minXOffset);
			break;
			
		case MinuetoKeyboard.KEY_RIGHT:
			xOffset+=X_OFFSET_STEP;
			xOffset = Math.min(xOffset, maxXOffset);
			break;
		}
		if (xOffset!=oldXOffset || yOffset!=oldYOffset) {
			setChanged();
			notifyObservers();
		}
	}

	@Override
	public void handleKeyRelease(int arg0) {

	}

	@Override
	public void handleKeyType(char arg0) {
		
	}


	@Override
	public MinuetoImage getImage()
	{
		final MinuetoImage img = mapDisp.getImage();
		return img.crop(xOffset, yOffset, Math.min(area.getWidth(), img.getWidth()), Math.min(area.getHeight(), img.getHeight()));
	}


	@Override
	public void handleMouseClick(int x, int y, int button) {
		mapDisp.handleMouseClick(x+xOffset, y+yOffset, button);
	}

	/* ========================
	 * 		Static methods
	 * ========================
	 */

}