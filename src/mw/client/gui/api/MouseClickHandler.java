package mw.client.gui.api;

import org.minueto.handlers.MinuetoMouseHandler;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

public class MouseClickHandler implements MinuetoMouseHandler {

	/*protected int coordX, coordY;
	protected int width, height;*/
	protected WindowArea area;
	protected Clickeable clickeable;
	private boolean clicking;
	
	/* ========================
	 * 		Constructors
	 * ========================
	 */

	public MouseClickHandler(int x, int y, int width, int height, Clickeable clickTarget) {
		area = new WindowArea(x, y, width, height);
		/*coordX = x;
		coordY = y;
		this.width =width;
		this.height = height;*/
		clickeable = clickTarget;
		clicking = false;
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
		}
	}

	@Override
	public void handleMousePress(int x, int y, int button) {
		clicking = area.containsPoint(x, y);
	}

	@Override
	public void handleMouseRelease(int x, int y, int button) {
		if (clicking)
			clickeable.handleMouseClick(x, y, button);
	}



	/* ========================
	 * 		Static methods
	 * ========================
	 */

}