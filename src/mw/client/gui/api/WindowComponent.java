package mw.client.gui.api;

import org.minueto.image.MinuetoDrawingSurface;

public final class WindowComponent extends ContainerComponent {

	public WindowComponent(int x, int y, int width, int height,	InterfaceComponent component) {
		super(x, y, width, height, component);
	}
	
	public void drawOn(MinuetoDrawingSurface canvas)
	{
		canvas.draw(getImage(), coordX, coordY);
	}
}