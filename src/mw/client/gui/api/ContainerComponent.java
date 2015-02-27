package mw.client.gui.api;

import org.minueto.image.MinuetoImage;

public class ContainerComponent extends WindowComponent {

	private InterfaceComponent subComponent;
	
	/* ========================
	 * 		Constructors
	 * ========================
	 */

	public ContainerComponent(int x, int y, int width, int height, InterfaceComponent component)
	{
		super(x, y, width, height);
		subComponent = component;
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