package mw.client.gui.api.components;

import java.util.Observable;
import java.util.Observer;

import org.minueto.image.MinuetoDrawingSurface;
import org.minueto.image.MinuetoImage;

import mw.client.gui.api.basics.AbstractWindowComponent;
import mw.client.gui.api.basics.ObservableWindowComponent;

public class BlockComponent extends AbstractWindowComponent implements Observer {
	
	
	private final ObservableWindowComponent comp;
	
	/* ========================
	 * 		Constructors
	 * ========================
	 */


	public BlockComponent(int x, int y, int width, int height, ObservableWindowComponent component)
	{
		super(x, y, width, height);
		comp = component;
		comp.addObserver(this);
	}

	public BlockComponent(int width, int height, ObservableWindowComponent component)
	{
		this(0,0,width, height, component);
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

	@Override
	public MinuetoImage getImage()
	{
		return comp.getImage();
	}

	@Override
	public void drawOn(MinuetoDrawingSurface canvas)
	{
		comp.drawOn(canvas);
	}
	
	@Override
	public void setPosition(int x, int y)
	{
		super.setPosition(x, y);
		comp.setPosition(x, y);
	}
	
	@Override
	public void updatePosition(int xAdd, int yAdd)
	{
		super.updatePosition(xAdd, yAdd);
		comp.updatePosition(xAdd, yAdd);
	}
	
	@Override
	public void update(Observable o, Object arg)
	{
		ChangedState changedState = (ChangedState)arg;
		if (changedState == ChangedState.SIZE)
		{
			setChanged(ChangedState.IMAGE);
			if (comp.getWidth() > getWidth()) {
				area.setWidth(comp.getWidth());
				setChanged(ChangedState.SIZE);
			}
			if (comp.getHeight() > getHeight()) {
				area.setHeight(comp.getHeight());
				setChanged(ChangedState.SIZE);
			}
		}
		else {
			setChanged(changedState);
		}
		notifyObservers();
	}


	/* ========================
	 * 		Static methods
	 * ========================
	 */

}