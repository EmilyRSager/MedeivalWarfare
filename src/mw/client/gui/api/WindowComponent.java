package mw.client.gui.api;

import org.minueto.image.MinuetoDrawingSurface;

public abstract class WindowComponent implements InterfaceComponent {

	protected int coordX, coordY; 
	protected int width, height;
	
	public WindowComponent(int x, int y, int width, int height) {
		coordX=x;
		coordY=y;
		this.width=width;
		this.height=height;
	}
	
	public void drawOn(MinuetoDrawingSurface canvas)
	{
		canvas.draw(getImage(), coordX, coordY);
	}
}