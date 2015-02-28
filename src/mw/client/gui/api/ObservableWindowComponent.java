package mw.client.gui.api;

import java.util.Observable;

import org.minueto.image.MinuetoDrawingSurface;


public abstract class ObservableWindowComponent extends Observable implements WindowComponent {

	//protected final Observable observable;
	protected WindowArea area;


	/* ========================
	 * 		Constructors
	 * ========================
	 */

	public ObservableWindowComponent(int x, int y, int width, int height) {
		super();
		area = new WindowArea (x,y,width,height);
	}


	/* ==========================
	 * 		Public methods
	 * ==========================
	 */
	/*public void addObserver(Observer o) {
		observable.addObserver(o);
	}

	protected void clearChanged() {
		observable.clearChanged();
	}

	public int countObservers() {
		return observable.countObservers();
	}

	public void deleteObserver(Observer o) {
		observable.deleteObserver(o);
	}

	public void deleteObservers() {
		observable.deleteObservers();
	}

	public boolean hasChanged() {
		return observable.hasChanged();
	}

	public void notifyObservers() {
		observable.notifyObservers();
	}

	public void notifyObservers(Object arg) {
		observable.notifyObservers(arg);
	}

	protected void setChanged() {
		observable.setChanged();
	}*/

	/* ==========================
	 * 		Private methods
	 * ==========================
	 */


	/* ==========================
	 * 		Inherited methods
	 * ==========================
	 */

	@Override
	public void drawOn(MinuetoDrawingSurface canvas)
	{
		//canvas.draw(getImage(), area.getLeftBorder(), area.getTopBorder());
		canvas.draw(getImage().crop(0, 0, area.getWidth(), area.getHeight()), area.getLeftBorder(), area.getTopBorder());
	}


	/* ========================
	 * 		Static methods
	 * ========================
	 */

}