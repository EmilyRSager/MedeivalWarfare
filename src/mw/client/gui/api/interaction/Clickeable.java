package mw.client.gui.api.interaction;

/**
 * The Clickeable interface defines something that can be clicked, without taking care of the logic of the clicks.
 * It is used by MouseClickHandlers.
 * @author Hugo
 *
 */
public interface Clickeable {

	/**
	 * Defines what needs to be done when this Clickeable is clicked.
	 * @param x the x coordinate of the click
	 * @param y the y coordinate of the click
	 * @param button the mouse button that triggered the click
	 */
	public void handleMouseClick(int x, int y, int button);

}