package mw.server.gamelogic;

import java.io.Serializable;
import java.util.Collection;
import java.util.Random;

public class RandomColorGenerator implements Serializable{
	static Random R = new Random (); 

	
	
	public static Color generateRandomColor(Collection<Color> pColors) 
    {
		Object[] lColors = pColors.toArray();
		return (Color) lColors[R.nextInt(lColors.length)];
	}

}
