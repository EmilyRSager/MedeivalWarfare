package mw.client.gui.api;

/**
 * The WindowArea class defines a basic squared area, that is supposed to represent an area on the screen.
 * @author hugo.kapp
 *
 */
public final class WindowArea {

	/*     x
	 *     ->
	 * y |
	 *   V
	 * 
	 */
	
	private int coordX, coordY;
	private int width, height;

	/* ========================
	 * 		Constructors
	 * ========================
	 */

	/**
	 * Creates a new WindowArea with a position, a width and a height.
	 * @param x the x coordinate of the new WindowArea
	 * @param y the y coordinate of the new WindowArea
	 * @param width the width of the new WindowArea
	 * @param height the height of the new WindowArea
	 */
	public WindowArea (int x, int y, int width, int height)
	{
		coordX=x;
		coordY=y;
		this.width=width;
		this.height=height;
	}

	/* ==========================
	 * 		Public methods
	 * ==========================
	 */

	/**
	 * Returns the coordinate of the left border of this WindowArea
	 * @return the coordinate of the left border of this WindowArea
	 */
	public int getLeftBorder() {
		return coordX;
	}

	/**
	 * Returns the coordinate of the right border of this WindowArea
	 * @return the coordinate of the right border of this WindowArea
	 */
	public int getRightBorder() {
		return coordX+width-1;
	}

	/**
	 * Returns the coordinate of the top border of this WindowArea
	 * @return the coordinate of the top border of this WindowArea
	 */
	public int getTopBorder() {
		return coordY;
	}

	/**
	 * Returns the coordinate of the bottom border of this WindowArea
	 * @return the coordinate of the bottom border of this WindowArea
	 */
	public int getBottomBorder() {
		return coordY+height-1;
	}

	/**
	 * Returns the width of this WindowArea
	 * @return the width of this WindowArea
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * Returns the height of this WindowArea
	 * @return the height of this WindowArea
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * Sets the position of this WindowArea
	 * @param x the new x coordinate of this WindowArea
	 * @param y the new y coordinate of this WindowArea
	 */
	public void setPosition(int x, int y) {
		coordX = x;
		coordY = y;
	}
	
	/**
	 * Sets the height of this WindowArea
	 * @param newHeight the new height of this WindowArea
	 * @throws IllegalArgumentException if the new height is invalid
	 */
	public void setHeight(int newHeight) {
		if (newHeight>=0)
			height=newHeight;
		else
			throw new IllegalArgumentException("Height must be positive, "+newHeight+" is invalid");
	}
	
	/**
	 * Sets the width of this WindowArea
	 * @param newWidth the new width of this WindowArea
	 * @throws IllegalArgumentException if the new width is invalid
	 */
	public void setWidth(int newWidth) {
		if (newWidth>=0)
			width=newWidth;
		else
			throw new IllegalArgumentException("Width must be positive, "+newWidth+" is invalid");
	}
	
	/**
	 * Returns whether or not this WindowArea contains the given point
	 * @param x the x coordinate of the point to consider
	 * @param y the y coordinate of the point to consider
	 * @return whether or not this WindowArea contains the point with coordinates (x,y)
	 */
	public boolean containsPoint(int x, int y)
	{
		return (x>=getLeftBorder() && y>=getTopBorder() && x<getRightBorder() && y<getBottomBorder());
	}
	
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