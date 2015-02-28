package mw.client.gui.api;

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

	public int getLeftBorder() {
		return coordX;
	}
	
	public int getRightBorder() {
		return coordX+width;
	}
	
	public int getTopBorder() {
		return coordY;
	}
	
	public int getBottomBorder() {
		return coordY+height;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}

	public void setHeight(int newHeight) {
		if (newHeight>=0)
			height=newHeight;
	}
	
	public void setWidth(int newWidth) {
		if (newWidth>=0)
			width=newWidth;
	}
	
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