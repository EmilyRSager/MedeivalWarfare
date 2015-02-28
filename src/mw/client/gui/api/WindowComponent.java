package mw.client.gui.api;

import org.minueto.image.MinuetoDrawingSurface;

public interface WindowComponent extends Displayable {

	public void drawOn(MinuetoDrawingSurface canvas);

}