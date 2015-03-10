package mw.client.gui.api;

import java.util.Observable;

import org.minueto.image.MinuetoDrawingSurface;
import org.minueto.image.MinuetoImage;

/**
 * The AbstractWindowComponent class provides a basic implementation for most of the methods inherited from 
 * the WindowComponent interface. The only method that needs to be implemented by all the subclasses
 * of AbstractWindowComponent to be working is the getImage method from Displayable.
 * @author Hugo Kapp
 *
 */
public abstract class AbstractWindowComponent extends Observable implements WindowComponent {

	/*     x
	 *     ->
	 * y |
	 *   V
	 * 
	 */
	
	protected final WindowArea area;
	private int minWidth, minHeight;
	
	/* ========================
	 * 		Constructors
	 * ========================
	 */
	
	/**
	 * Creates a new AbstractWindowComponent with a position, a width and a height
	 * @param x the x coordinate of the new AbstractWindowComponent
	 * @param y the y coordinate of the new AbstractWindowComponent
	 * @param width the width of the new AbstractWindowComponent
	 * @param height the height of the new AbstractWindowComponent
	 */
	public AbstractWindowComponent(int x, int y, int width, int height) {
		super();
		area = new WindowArea(x, y, width, height);
		minWidth = width;
		minHeight = height;
	}
	

	/* ==========================
	 * 		Inherited methods
	 * ==========================
	 */
	
	
	@Override
	public void drawOn(MinuetoDrawingSurface canvas)
	{
		MinuetoImage croppedImg = ExtendedMinuetoImage.cropToFitArea(getImage(), area);
		canvas.draw(croppedImg, area.getLeftBorder(), area.getTopBorder());
	}
	
	
	@Override
	public int getWidth()
	{
		return area.getWidth();
	}
	
	@Override
	public int getHeight()
	{
		return area.getHeight();
	}
	
	@Override
	public void setPosition(int x, int y)
	{
		area.setPosition(x, y);
	}
	
	/*@Override
	public void setSize(int newWidth, int newHeight)
	{
		area.setWidth(newWidth);
		area.setHeight(newHeight);
	}
	
	@Override
	public int getMinWidth() {
		return minWidth;
	}
	
	@Override
	public int getMinHeight() {
		return minHeight;
	}*/
}