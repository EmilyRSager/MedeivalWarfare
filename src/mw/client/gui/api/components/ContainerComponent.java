package mw.client.gui.api.components;

import mw.client.gui.api.basics.AbstractWindowComponent;
import mw.client.gui.api.basics.Displayable;

import org.minueto.image.MinuetoImage;

@Deprecated
public class ContainerComponent extends AbstractWindowComponent {

	protected Displayable subComponent;
	
	/* ========================
	 * 		Constructors
	 * ========================
	 */

	public ContainerComponent(int x, int y, int width, int height, Displayable component)
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

	/*@Override
	public void handleMouseClick(int x, int y, int button) {
		if (x>=coordX
				&& y>=coordY
				&& x < coordX+width
				&& y < coordY+height)
		{
			subComponent.handleMouseClick(x-coordX, y-coordY, button);
		}
	}*/
}