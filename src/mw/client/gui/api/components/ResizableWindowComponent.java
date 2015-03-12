package mw.client.gui.api.components;

import mw.client.gui.api.basics.AbstractWindowComponent;

import org.minueto.image.MinuetoImage;

public abstract class ResizableWindowComponent extends AbstractWindowComponent {

	private int minWidth, minHeight;
	
	/* ========================
	 * 		Constructors
	 * ========================
	 */

	public ResizableWindowComponent(int x, int y, int width, int height)
	{
		super(x,y,width,height);
		minWidth = width;
		minHeight = height;
	}

	/* ==========================
	 * 		Public methods
	 * ==========================
	 */


	/* ==========================
	 * 		Private methods
	 * ==========================
	 */


	/* ==========================
	 * 		Inherited methods
	 * ==========================
	 */



	/* ========================
	 * 		Static methods
	 * ========================
	 */

}