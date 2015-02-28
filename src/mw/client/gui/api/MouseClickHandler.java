package mw.client.gui.api;

import org.minueto.handlers.MinuetoMouseHandler;

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
	public void handleMouseMove(int arg0, int arg1) {
		if (clicking) {
			clicking = area.containsPoint(arg0, arg1);
		}
	}

	@Override
	public void handleMousePress(int arg0, int arg1, int arg2) {
		clicking = area.containsPoint(arg0, arg1);
	}

	@Override
	public void handleMouseRelease(int arg0, int arg1, int arg2) {
		if (clicking)
			clickeable.handleMouseClick(arg0, arg1, arg2);
	}



	/* ========================
	 * 		Static methods
	 * ========================
	 */

}