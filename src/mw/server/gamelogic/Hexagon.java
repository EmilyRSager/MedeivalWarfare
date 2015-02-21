package mw.server.gamelogic;

import java.util.ArrayList;

public interface Hexagon 
{
	public void initializeNeighbors(ArrayList<Hexagon> pHexes); 
	public int[] getCoordinates(); 
	public ArrayList<Hexagon> getNeighbors(); 

}
