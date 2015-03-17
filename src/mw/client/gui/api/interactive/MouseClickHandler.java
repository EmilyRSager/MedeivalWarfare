package mw.client.gui.api.interactive;

import mw.client.gui.api.basics.WindowArea;

import org.minueto.handlers.MinuetoMouseHandler;

/**
 * The MouseClickHandler class is a useful class to hide all the logic behind a click action, and only specify what needs to be
 * done when a click happens, which is done through the handleMouseClick method of Clickeable.
 * @author Hugo Kapp
 *
 */
public class MouseClickHandler implements MinuetoMouseHandler {

	protected WindowArea area;
	protected Clickeable clickeable;
	
	private boolean clicking;
	private boolean moved;
	
	/* ========================
	 * 		Constructors
	 * ========================
	 */
	
	/**
	 * Creates a new MouseClickHandler with the given WindowArea and a Clickeable that defines what needs to be done when a click occurs.
	 * It is strongly recommended that the given WindowArea is a reference to the WindowArea of the corresponding WindowComponent,
	 * so that the changes performed on the WindowComponent's WindowArea are done on the WindowArea of the MouseClickHandler as well.
	 * @param clickeableArea the WindowArea in which a click can occur
	 * @param clickTarget the Clickeable that defines what needs to be done when a click occurs
	 */
	public MouseClickHandler(WindowArea clickeableArea, Clickeable clickTarget)
	{
		area = clickeableArea;
		clickeable = clickTarget;
		clicking = false;
		moved= false;
	}

	@Deprecated
	public MouseClickHandler(int x, int y, int width, int height, Clickeable clickTarget) {
		this(new WindowArea(x, y, width, height), clickTarget);
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
	public void handleMouseMove(int x, int y) {
		if (clicking) {
			clicking = area.containsPoint(x, y);
			moved=true;
		}
	}

	@Override
	public void handleMousePress(int x, int y, int button) {
		clicking = area.containsPoint(x, y);
		moved=false;
	}

	@Override
	public void handleMouseRelease(int x, int y, int button) {
		if (clicking && !moved)
			clickeable.handleMouseClick(x, y, button);
		moved=false;
		clicking=false;
	}



	/* ========================
	 * 		Static methods
	 * ========================
	 */

}