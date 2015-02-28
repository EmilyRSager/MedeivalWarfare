package mw.client.controller;

import mw.client.gui.api.ExtendedMinuetoColor;
import mw.shared.SharedColor;

import org.minueto.MinuetoColor;

/**
 * The ModelGUITranlator controller translates any information from the Model to a format
 * that the GUI can handle
 * @author Hugo Kapp
 *
 */
public final class ModelGUITranslator {

	/**
	 * Translates the given SharedColor to a MinuetoColor
	 * @param sc the SharedColor to translate
	 * @return a MinuetoColor representing correctly the given SharedColor
	 */
	public static MinuetoColor translateToMinuetoColor(SharedColor sc)
	{
		switch (sc)
		{
		case BLUE:
			return MinuetoColor.BLUE;
			
		case GREEN:
			return MinuetoColor.GREEN;
			
		case GREY:
			return ExtendedMinuetoColor.GREY;
			
		case RED:
			return MinuetoColor.RED;
			
		case YELLOW:
			return MinuetoColor.YELLOW;
			
			default:
				throw new IllegalArgumentException("Unrecognized SharedColor value : "+sc);
		}
	}


}