package mw.client.controller;

import mw.shared.SharedColor;

import org.minueto.MinuetoColor;

public final class ModelGUITranslator {

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

	
	public static MinuetoColor averageColors(MinuetoColor a, MinuetoColor b)
	{
		int red = (a.getRed()+b.getRed())/2;
		int green = (a.getGreen()+b.getGreen())/2;
		int blue = (a.getBlue()+b.getBlue())/2;
		return new MinuetoColor(red, green, blue);
	}
}