package mw.client.gui.api;

import org.minueto.image.MinuetoDrawingSurface;

/**
 * The Drawable interface represents anything that knows how to draw itself on a canvas (a
 * MinuetoDrawingSurface). Note that this implies that it knows where it should be drawn on
 * the given canvas (information about the position is necessary)
 * @author Hugo Kapp
 *
 */
public interface Drawable extends Displayable {

	/**
	 * Draws this Drawable on the given MinuetoDrawingSurface
	 * @param canvas the MinuetoDrawingSurface to draw on
	 */
	public void drawOn(MinuetoDrawingSurface canvas);

}