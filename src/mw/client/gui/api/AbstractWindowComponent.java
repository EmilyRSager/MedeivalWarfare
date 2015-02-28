package mw.client.gui.api;

import org.minueto.image.MinuetoDrawingSurface;

public abstract class AbstractWindowComponent implements WindowComponent {

	/*     x
	 *     ->
	 * y |
	 *   V
	 * 
	 */
	
	/*protected int coordX, coordY;
	protected int width, height;
	
	public AbstractWindowComponent(int x, int y, int width, int height) {
		coordX=x;
		coordY=y;
		this.width=width;
		this.height=height;
	}
	
	@Override
	public void drawOn(MinuetoDrawingSurface canvas)
	{
		canvas.draw(getImage().crop(0, 0, width, height), coordX, coordY);
	}*/
	
	protected WindowArea area;
	
	public AbstractWindowComponent(int x, int y, int width, int height) {
		area = new WindowArea(x, y, width, height);
	}
	
	@Override
	public void drawOn(MinuetoDrawingSurface canvas)
	{
		canvas.draw(getImage().crop(0, 0, area.getWidth(), area.getHeight()), area.getLeftBorder(), area.getTopBorder());
	}
}