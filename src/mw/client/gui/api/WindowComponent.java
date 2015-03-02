package mw.client.gui.api;

/**
 * The WindowComponent interface describes a Drawable that knows about its coordinates,
 * and provides access to these (modification of the position and information about
 * its width and height). It is used by Layouts to modify their arrangement.
 * @author Hugo Kapp
 *
 */
public interface WindowComponent extends Drawable {
	
	/**
	 * Returns the current width of this WindowComponent
	 * @return the current width of this WindowComponent
	 */
	public int getWidth();
	
	/**
	 * Returns the current height of this WindowComponent
	 * @return the current height of this WindowComponent
	 */
	public int getHeight();
	
	/**
	 * Set the position of this WindowComponent to (x,y)
	 * @param x the new x coordinate of this WindowComponent
	 * @param y the new y coordinate of this WindowComponent
	 */
	public void setPosition(int x, int y);
}