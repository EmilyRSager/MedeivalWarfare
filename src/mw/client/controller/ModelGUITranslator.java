package mw.client.controller;

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
			return averageColors(MinuetoColor.BLACK, MinuetoColor.WHITE);
			
		case RED:
			return MinuetoColor.RED;
			
		case YELLOW:
			return MinuetoColor.YELLOW;
			
			default:
				throw new IllegalArgumentException("Unrecognized SharedColor value : "+sc);
		}
	}

	/**
	 * Gives the average of two MinuetoColors, passed a arguments
	 * @param a the first Color
	 * @param b the second Color
	 * @return the average of the two colors
	 */
	public static MinuetoColor averageColors(MinuetoColor a, MinuetoColor b)
	{
		int red = (a.getRed()+b.getRed())/2;
		int green = (a.getGreen()+b.getGreen())/2;
		int blue = (a.getBlue()+b.getBlue())/2;
		return new MinuetoColor(red, green, blue);
	}
}