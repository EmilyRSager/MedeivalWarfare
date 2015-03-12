package mw.client.controller;

import mw.client.gui.api.extminueto.ExtendedMinuetoColor;
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
		MinuetoColor color;
		switch (sc)
		{
		case BLUE:
			color = MinuetoColor.BLUE;
			color = ExtendedMinuetoColor.mixColors(color, MinuetoColor.WHITE, 0.75);
			break;
			
		case GREEN:
			color = MinuetoColor.GREEN;
			break;
			
		case GREY:
			color = ExtendedMinuetoColor.GREY;
			break;
			
		case RED:
			color = MinuetoColor.RED;
			break;
			
		case YELLOW:
			color = MinuetoColor.YELLOW;
			break;
			
			default:
				throw new IllegalArgumentException("Unrecognized SharedColor value : "+sc);
		}
		
		color = color.darken(0.05);
		return color;
	}


}