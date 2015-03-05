package mw.server.gamelogic;

import java.util.Observable;

/**
 * @author emilysager
 */

public class Tile  extends Observable
{
	
	private StructureType aStructureType; 
	private VillageType aVillageType; 
    private Unit aUnit; 
    private boolean aHasMeadow;
    private int aX; 
    private int aY; 
    private Color myColor;

   
    
    
    public Tile(StructureType pStructureType, int pX, int pY) 
    { 
    	aX = pX; 
    	aY = pY; 
    	aStructureType = pStructureType; 
    	myColor = Color.NEUTRAL;
    	aHasMeadow = false; 
    	aVillageType = VillageType.NO_VILLAGE; 
    }

    public int [] getTileCoordinates()
    {
    	int[] rCoord = {aX, aY}; 
    	return rCoord; 
    }
    
    public void setColor(Color c) 
    {
       myColor = c; 
       setChanged();
    }

    public void setNeutral() {
        myColor = Color.NEUTRAL; 
        setChanged();
    }
    public StructureType getStructureType() {
       return aStructureType;
    }
    public void setStructureType(StructureType pStructureType) {
        pStructureType = aStructureType; 
        setChanged();
    }
    public boolean getMeadow() {
      
        return aHasMeadow; 
    }
    public void setHasMeadow(boolean pHasMeadow) {
       aHasMeadow = pHasMeadow; 
       setChanged();
    }
    public Color getColor() 
    {
        return myColor; 
    }
	public Unit getUnit() 
	{
		return aUnit; 
	}
	public void setUnit(Unit pUnit)
	{
		aUnit = pUnit; 
		 setChanged();
	}
	public boolean isProtected(UnitType pType)
	{
		return false; 
	}

	public void setVillageType(VillageType pVillageType)
	{
		aVillageType = pVillageType; 
		 setChanged();
	}
	
	public VillageType getVillageType()
	{
		return aVillageType; 
		
	}
	
	

	
}
