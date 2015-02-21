package mw.server.gamelogic;

import java.util.ArrayList;



/**
 * @author emilysager
 */
public class Tile implements Hexagon{
    

    private StructureType aStructureType; 
    private Unit aUnit; 
    private boolean aHasMeadow;
    private GameMap aMap;
    private final int aX;
    private final int aY;
    private Color myColor;
    private ArrayList<Hexagon> aNeighbors; 
   
    
    public Tile(StructureType pStructureType, int pX, int pY) 
    { 
    	aX= pX; 
    	aY = pY; 
    	aStructureType = pStructureType; 
    	myColor = Color.NEUTRAL;
    	aHasMeadow = false; 
    }
   
    @Override
    public void initializeNeighbors(ArrayList<Hexagon> pNeighbors)
    {
    	aNeighbors =  pNeighbors; 
    }
    public ArrayList<Hexagon> getNeighbors (){
    	return aNeighbors; 
      
    }
    public void setColor(Color c) {
       myColor = c; 
    }


    public void setNeutral() {
        myColor = Color.NEUTRAL; 
    }

    public StructureType getStructureType() {
       return aStructureType;
    }

    public void setStructureType(StructureType pStructureType) {
        pStructureType = aStructureType; 
    }

    public boolean getMeadow() {
      
        return aHasMeadow; 
    }

    public void setHasMeadow(boolean pHasMeadow) {
       aHasMeadow = pHasMeadow; 
    }

    public Color getColor() {
        return myColor; 
    }

	public Unit getUnit() 
	{
		return aUnit; 
	}
	public void setUnit(Unit pUnit)
	{
		aUnit = pUnit; 
	}
	public boolean isProtected(UnitType pType)
	{
		return false; 
	}
	public Village getVillage()
	{
		 Village myVillage = new Village(aMap.getSameColorTiles(this));
		 return myVillage; 
	}

	public void setVillage(Village invadingVillage) {
		// TODO Auto-generated method stub
		
	}

	public int[] getCoordinates() {
		int [] toRet = {aX, aY};
		return toRet; 
		
	}

	

	
}
