package mw.server.gamelogic;

import java.util.Random;

public class RandomColorGenerator {
	Random R = new Random (); 
	
	public  Color generateRandomColor() 
    {
		
    	int i = R.nextInt(5); 
    	if (i == 0)
    	{
    		return Color.BLUE;
    	}
    	if (i == 1)
    	{
    		return Color.RED;
    	}
    	if (i == 2)
    	{
    		return Color.GREEN;
    	}
    	if (i==3)
    	{
    		return Color.YELLOW;
    	}
    	if (i==4)
    	{
    		return Color.SEATILE;
    	}
    	if (i==5)
    	{
    		return Color.NEUTRAL;
    	}
    	return Color.NEUTRAL; //hopefully this code is unreachable
		
	}

}
