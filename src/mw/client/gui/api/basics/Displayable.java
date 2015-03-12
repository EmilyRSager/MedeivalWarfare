package mw.client.gui.api.basics;

import org.minueto.image.MinuetoImage;

/**
 * A Displayable is anything that has a representation in the form of a MinuetoImage
 * @author Hugo Kapp
 *
 */
public interface Displayable {

	/**
	 * Returns an image representation of this Displayable 
	 * @return a MinuetoImage representing this Displayable
	 */
	public MinuetoImage getImage();
}