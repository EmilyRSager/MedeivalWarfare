package mw.client.gui.api;

import org.minueto.handlers.MinuetoMouse;
import org.minueto.image.MinuetoImage;

public abstract class AbstractButton implements InterfaceComponent {

	private int coordX, coordY;
	private int width, height;
	
	public AbstractButton(int x, int y, int width, int height) {
		coordX=x;
		coordY = y;
		this.width=width;
		this.height=height;
	}
	
	@Override
	public MinuetoImage getImage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void handleMouseClick(int x, int y, int button) {
		if (x>=coordX
				&& y>=coordY
				&& x<coordX+width
				&& y<coordY+height
				&& button == MinuetoMouse.MOUSE_BUTTON_LEFT)
		{
			buttonClick();
		}
	}

	public abstract void buttonClick();
}