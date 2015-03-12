package mw.server.gamelogic.state;


import java.io.Serializable;
import java.util.Observable;

import mw.server.gamelogic.enums.Color;
import mw.server.gamelogic.enums.StructureType;
import mw.server.gamelogic.enums.UnitType;
import mw.server.gamelogic.enums.VillageType;

import com.google.gson.Gson;


/**
 * @author emilysager
 */

public class Tile  extends Observable implements Serializable
{
	
	private StructureType aStructureType; 
	private VillageType aVillageType; 
    private Unit aUnit; 
    private boolean aHasMeadow;
    private int aX; 
    private int aY; 
    private Color myColor;
    private int aWood; 
    private int aGold; 

   
    
    
    public Tile(StructureType pStructureType, int pX, int pY) 
    { 
    	aX = pX; 
    	aY = pY; 
    	aStructureType = pStructureType; 
    	myColor = Color.NEUTRAL;
    	aHasMeadow = false; 
    	aVillageType = VillageType.NO_VILLAGE;
    	aGold = 0;
    	aWood = 0; 
    	
    }
    
    public boolean hasUnit()
    {
    	return aUnit != null;
    }
    
    @Override
    public String toString(){
    	return new Gson().toJson(this);
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
    public StructureType getStructureType() 
    {
       return aStructureType;
    }
 
    public void setStructureType(StructureType pStructureType) 
    {
         aStructureType = pStructureType; 
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
	
	public void setWood(int pWood)
	{
		if (aVillageType != VillageType.NO_VILLAGE)
		{
			aWood = pWood;
		}
		setChanged();
	}
	public void setGold(int pGold)
	{
		if (aVillageType != VillageType.NO_VILLAGE)
		{
			aGold = pGold;
		}
		setChanged();
	}

	public int getGold()
	{
		return aGold; 
	}
	
	public int getWood() 
	{
		return aWood;
	}
	
	public void setVillageType(VillageType pVillageType)
	{
		 aVillageType = pVillageType; 
		 if (aVillageType != VillageType.NO_VILLAGE) 
		 {
			 setStructureType(StructureType.VILLAGE_CAPITAL);
			 
		 }
		 if (pVillageType == VillageType.NO_VILLAGE)
		 {
			 setStructureType(StructureType.NO_STRUCT);
		 }
		 setChanged();
	}
	
	
	public VillageType getVillageType()
	{
		return aVillageType; 	
	}
	
	
	

	
}
