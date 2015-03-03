package mw.server.gamelogic;

/**
 * @author emilysager
 */

public class Tile  extends AbstractGraphNode 
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
    }

    public int [] getTileCoordinates()
    {
    	int[] rCoord = {aX, aY}; 
    	return rCoord; 
    }
    
    public void setColor(Color c) 
    {
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

	public VillageType getVillageType() {
		return aVillageType; 
		
	}
	
	

	
}
