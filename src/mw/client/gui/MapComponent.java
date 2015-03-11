package mw.client.gui;

import mw.client.gui.api.AbstractWindowComponent;
import mw.client.gui.api.Clickeable;
import mw.client.gui.api.MouseClickHandler;
import mw.client.gui.api.WindowArea;

import org.minueto.MinuetoColor;
import org.minueto.MinuetoEventQueue;
import org.minueto.handlers.MinuetoKeyboard;
import org.minueto.handlers.MinuetoKeyboardHandler;
import org.minueto.image.MinuetoImage;
import org.minueto.window.MinuetoFrame;

public final class MapComponent extends AbstractWindowComponent implements
		Clickeable, MinuetoKeyboardHandler {
	
	private static final int X_OFFSET_STEP = 10;
	private static final int Y_OFFSET_STEP = 10;
	
	private int xOffset, yOffset;
	private final int minXOffset, minYOffset;
	private final int maxXOffset, maxYOffset;
	private final MapDisplay mapDisp;
	private final MouseClickHandler clickHandler;

	/* ========================
	 * 		Constructors
	 * ========================
	 */
	
	public MapComponent(int x, int y, int width, int height, MapDisplay mapDisp) {
		super(x, y, Math.min(width, mapDisp.getWidth()), Math.min(height, mapDisp.getHeight()));
		this.mapDisp = mapDisp;
		clickHandler = new MouseClickHandler(area, this);
		
		minXOffset=0;
		minYOffset=0;
		maxXOffset=Math.max(mapDisp.getWidth()-width, 0);
		maxYOffset=Math.max(mapDisp.getHeight()-height, 0);
		
		xOffset = minXOffset;
		yOffset = minYOffset;
		
	}

	public MapComponent(int width, int height, MapDisplay mapDisp) {
		this(0, 0, width, height, mapDisp);
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
		//this.addObserver(window);
	}
	
	/* ==========================
	 * 		Inherited methods
	 * ==========================
	 */
	

	@Override
	public void handleKeyPress(int button) {
		final int oldYOffset = yOffset;
		final int oldXOffset = xOffset;
		switch(button)
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

	/*public static void main(String[] args)
	{
		final int width = 12;
		final int height = 12;
		ImageTile[][] tiles = new ImageTile[width][height];
		for (int i=0; i<width; i++)
		{
			for (int j=0; j<height; j++)
			{
				tiles[i][j] = new ImageTile();
				tiles[i][j].updateColor(MinuetoColor.RED);
			}
		}
		MapDisplay mapDisp = new MapDisplay(tiles);
		MapComponent comp = new MapComponent(0, 0, 400, 400, mapDisp);
		
		MinuetoFrame window = new MinuetoFrame(400, 400, true);
		window.setVisible(true);
		window.draw(comp.getImage(), 0, 0);
		window.render();
		MinuetoEventQueue queue = new MinuetoEventQueue();
		window.registerMouseHandler(new MouseClickHandler(new WindowArea(0, 0, 400, 400), comp), queue);
		window.registerKeyboardHandler(comp, queue);
		
		while(true)
		{
			while(queue.hasNext()) {
				queue.handle();
				window.clear();
				window.draw(comp.getImage(), 0, 0);
				window.render();
			}
		}
	}*/
	
}