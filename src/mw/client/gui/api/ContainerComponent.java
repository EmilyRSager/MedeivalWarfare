package mw.client.gui.api;

import org.minueto.image.MinuetoImage;

public class ContainerComponent implements InterfaceComponent {

	private InterfaceComponent subComponent;
	protected int coordX, coordY; 
	private int width, height;
	
	/* ========================
	 * 		Constructors
	 * ========================
	 */

	public ContainerComponent(int x, int y, int width, int height, InterfaceComponent component)
	{
		subComponent = component;
		coordX=x;
		coordY=y;
		this.width=width;
		this.height=height;
	}

	/* ==========================
	 * 		Public methods
	 * ==========================
	 */

	@Override
	public MinuetoImage getImage()
	{
		return subComponent.getImage();
	}

	@Override
	public void handleMouseClick(int x, int y, int button) {
		if (x>=coordX
				&& y>=coordY
				&& x < coordX+width
				&& y < coordY+height)
		{
			subComponent.handleMouseClick(x-coordX, y-coordY, button);
		}
	}
}