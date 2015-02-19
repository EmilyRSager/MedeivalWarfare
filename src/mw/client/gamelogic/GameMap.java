package server;

import java.awt.List;
import java.util.ArrayList;



/**
 * GameMap class definition.
 * Generated by the TouchCORE code generator.
 */
public class GameMap {
    
	
	//TODO 
	//1. Randomly initialize trees 
	//2. GameMap needs to store all villages somehow
	//
    private ArrayList<Tile> tiles;
    
    public void partition(Color colors) {
        Village lVillage;
        ArrayList<Tile> aReachableTiles;
        for (Tile t : tiles) {
            t.setColor(colors);
        }
        for (Tile t : tiles) {
            aReachableTiles = t.getReachableTiles();  //this will get all the tiles that can be reached at this point in the construction of the map 
            if (aReachableTiles.size() >= 3) {
                lVillage = new Village(aReachableTiles); // need to make sure we haven't already initialized a village here 
            }
            if (aReachableTiles.size() < 3) 
            {
            	for (Tile lTile: aReachableTiles) 
            	{
            		lTile.setNeutral();
            	}
            }
        }
    }

    public static GameMap generateRandomMap() {
        /* TODO: No message view defined */
        return null;
    }

    public boolean getPath(Tile start, Tile end) {
        /* TODO: No message view defined */
        return false;
    }
    
    public boolean canFuse(Village v1, Village v2)
    {
    	return false; 
    }

	public void fuseVillages(Village invadedVillage, Village invadingVillage) {
		// TODO Auto-generated method stub
		
	}

	public ArrayList<Village> getNeighboringVillages(Tile pTile) {
		// TODO Auto-generated method stub
		return null; 
		
	}
}