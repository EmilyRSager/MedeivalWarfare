package mw.server.gamelogic.state;

import java.io.Serializable;
import java.util.Observable;

import mw.server.gamelogic.enums.Color;
import mw.server.gamelogic.enums.StructureType;
import mw.server.gamelogic.enums.UnitType;
import mw.server.gamelogic.enums.VillageType;
import mw.shared.Coordinates;

import com.google.gson.GsonBuilder;


/**
 * @author emilysager
 */
public class Tile extends Observable implements Serializable
{
	private StructureType aStructureType; 
	private VillageType aVillageType; 
    private Unit aUnit; 
    private boolean aHasMeadow;
    private int aX; 
    private int aY; 
    private Color myColor;
    
    /**
     * overloaded constructor
     * @param pX
     * @param pY
     */
    public Tile (int pX, int pY)
    {
    	 this(StructureType.NO_STRUCT, pX, pY);
    }
    
    /**
     * Tile constructor
     * @param pStructureType
     * @param pX
     * @param pY
     */
    public Tile(StructureType pStructureType, int pX, int pY)  { 
    	aX = pX; 
    	aY = pY; 
    	aStructureType = pStructureType; 
    	myColor = Color.NEUTRAL;
    	aHasMeadow = false; 
    	aVillageType = VillageType.NO_VILLAGE;
    }
    
   /**
    *  @Override
    * @see java.lang.Object#toString()
    */
    public String toString()
    {
    	return new GsonBuilder().setPrettyPrinting().create().toJson(this);
    }

   /**
    * Returns the coordinates of a Tile in [x, y] form
    * @return
    */
    public Coordinates getCoordinates()
    {
    	return new Coordinates(aX, aY);
    }
    
  /**
   * Sets the color 
   * @param pColor
   */
    public void setColor(Color pColor) 
    {
       myColor = pColor; 
       setChanged();
    }
    
    /**
     * Returns the color property of tile
     * @return
     */
    public Color getColor() 
    {
        return myColor; 
    }

    /**
     * @return
     */
    public StructureType getStructureType() 
    {
       return aStructureType;
    }
 
    /**
     * Sets the structure type of a tile
     * @param pStructureType
     */
    public void setStructureType(StructureType pStructureType) 
    {
       	System.out.println("[Game] The tile with coordinates (" + aX + ", " + aY + ") is getting structure type set to " + pStructureType );
    	System.out.println("[Game] This change was made by " + (Thread.currentThread().getStackTrace())[2]);
        aStructureType = pStructureType; 
        setChanged();  
    }
 
    /**
     * @return
     */
    public boolean isMeadowOnTile() 
    {
        return aHasMeadow; 
    }
 
    /**
     * Sets if there is a meadow on a tile to true or false 
     * @param pHasMeadow
     */
    public void setMeadow(boolean pHasMeadow) 
    {
       aHasMeadow = pHasMeadow; 
       setChanged();  
    }
    
    /**
     * @return true if there is a Unit on this tile, false otherwise
     */
    public boolean hasUnit()
    {
    	return aUnit != null && aUnit.getUnitType() != UnitType.NO_UNIT;
    }
    
    /**
     * Returns the Unit on a tile if there is one, otherwise returns null 
     * @return
     */
	public Unit getUnit() 
	{
		return aUnit; 
	}
	
	/**
	 * Sets the current unit of a tile 
	 * @param pUnit
	 */
	public void setUnit(Unit pUnit)
	{
		aUnit = pUnit; 
		if (aUnit!=null)
		{
			System.out.println("[Game] The tile with coordinates (" + aX + ", " + aY + ") now has a " + aUnit.getUnitType() + " on it" );
			System.out.println("[Game] This change was made by " + (Thread.currentThread().getStackTrace())[2]);
		}
		setChanged();
	}
	
	/**
	 * Sets the villageType of the tile
	 * @param pVillageType
	 */
	public void setVillageType(VillageType pVillageType)
	{
		 aVillageType = pVillageType; 
		 setChanged();
	}
	
	/**
	 * @return
	 */
	public VillageType getVillageType()
	{
		return aVillageType; 	
	}

	public boolean isEmpty() {
		if (!hasUnit() && aVillageType == VillageType.NO_VILLAGE && aStructureType == StructureType.NO_STRUCT)
		{
			return true;
		}
		return false;
	}

	public void notifyChanged() {
		setChanged();
		notifyObservers();
	}
}
