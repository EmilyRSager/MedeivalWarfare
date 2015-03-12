package mw.client.gui.api.basics;

import mw.client.gui.api.extminueto.ExtendedMinuetoImage;

import org.minueto.image.MinuetoDrawingSurface;
import org.minueto.image.MinuetoImage;

/**
 * The AbstractWindowComponent class provides a basic implementation for most of the methods inherited from 
 * the WindowComponent interface. The only method that needs to be implemented by all the subclasses
 * of AbstractWindowComponent to be working is the getImage method from Displayable.
 * @author Hugo Kapp
 *
 */
public abstract class AbstractWindowComponent extends ObservableWindowComponent {

	/*     x
	 *     ->
	 * y |
	 *   V
	 * 
	 */
	
	
	protected final WindowArea area;
	
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
		if (x != area.getLeftBorder() || y != area.getRightBorder()) {
			area.setPosition(x, y);
			setChanged(ChangedState.POSITION);
		}
	}
	
	@Override
	public void updatePosition(int xAdd, int yAdd)
	{
		setPosition(area.getLeftBorder() + xAdd, area.getTopBorder() + yAdd);
	}
	
}