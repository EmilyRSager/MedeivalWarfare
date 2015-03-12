package mw.server.gamelogic.state;

import java.io.Serializable;
import java.util.Observable;
import mw.server.gamelogic.enums.Color;
import mw.server.gamelogic.enums.StructureType;
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
    
    /**
     * Tile constructor
     * @param pStructureType
     * @param pX
     * @param pY
     */
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
    
    /**
     * 
     * @return
     */
    public boolean hasUnit()
    {
    	return aUnit != null;
    }
    
   /**
    *  @Override
    * @see java.lang.Object#toString()
    */
    public String toString()
    {
    	return new Gson().toJson(this);
    }

   /**
    * Returns the coordinates of a Tile in [x, y] form
    * @return
    */
    public int [] getTileCoordinates()
    {
    	int[] rCoord = {aX, aY}; 
    	return rCoord; 
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
     * Returns the color property of tile
     * @return
     */
    public Color getColor() 
    {
        return myColor; 
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
		 setChanged();
	}

	/**
	 * adds wood to the tile.  should only be called on the village capital
	 * @param pWood
	 */
		public void setWood(int pWood)
	{
			aWood = pWood;
			setChanged();
	}
		
		/**
		 * adds gold to the tile.  should only be called on the village capital
		 * @param pGold
		 */
	public void setGold(int pGold)
	{
			aGold = pGold;
			setChanged();
	}

	/**
	 * Returns the gold of the village if the tile is the village capital, otherwise returns 0; 
	 * @return
	 */
	public int getGold()
	{
		return aGold; 
	}
	
	/**
	 * Returns the wood of the village if the tile is the village capital, otherwise returns 0; 
	 * @return
	 */
	public int getWood() 
	{
		return aWood;
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
	 * 
	 * @return
	 */
	public VillageType getVillageType()
	{
		return aVillageType; 	
	}
}
