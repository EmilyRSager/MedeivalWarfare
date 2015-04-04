package mw.server.gamelogic.util;

import java.io.Serializable;
import java.util.Collection;
import java.util.Random;

import mw.server.gamelogic.enums.Color;

public class RandomColorGenerator implements Serializable{
	static Random R = new Random (); 

	
	
	public static Color generateRandomColor(Collection<Color> pColors) 
    {
		Object[] lColors = pColors.toArray();
		return (Color) lColors[R.nextInt(lColors.length)];
	}

}
