package mw.server.gamelogic;

import java.util.Collection;
import java.util.Random;

public class RandomColorGenerator {
	static Random R = new Random (); 

	
	
	public static Color generateRandomColor(Collection<Color> pColors) 
    {
	
		System.out.println(pColors.size());
		Object[] lColors = pColors.toArray();
		return (Color) lColors[R.nextInt(lColors.length)];
		
		
	}

}
