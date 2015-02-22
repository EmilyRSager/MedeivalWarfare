package mw.server.gamelogic;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

import javax.annotation.Generated;



/**
 * Village class definition.
 * @author emilysager
 */
public class Village implements Cloneable, Serializable{
	
	private int aGold; 
    private int aWood; 
    private int aUpkeepCost;
    private VillageType aVillageType; 
    private Collection<GraphNode> aVillageNodes; 
    
    
    public Village(Set<GraphNode> villageSet) 
    {
     aVillageNodes = villageSet; 
    }
    public Village(int pGold, int pWood)
    {
    	
    }
    public void generateGold()
    {
    	int addGold = 0;  
    	for (GraphNode lGraphNode: aVillageNodes)
    	{ 
    		addGold +=Logic.getGoldGenerated(lGraphNode);  
    	}
    	aGold += addGold; 
    	
    }
    public void upgradeVillage(VillageType pVillageType) throws NotEnoughIncomeException {
        int upgradeCost = PriceCalculator.getUpgradePrice(pVillageType);
        
        if (aWood >= upgradeCost) {
            try {
				aVillageType = Logic.upgrade(aVillageType);
			} catch (CantUpgradeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            aWood -= upgradeCost;
        }
        else 
        {
        	throw new NotEnoughIncomeException(aGold - upgradeCost); 
        }
    }

    /**
     * goes through the village and adds gold to the village based the number of meadows/tiles 
     * replaces any Tombstones with trees
     */
    public void updateTiles() {
        StructureType pStructureType;
        boolean meadow;
        for (Tile lTile : aTiles) {
            pStructureType = lTile.getStructureType();
            if (pStructureType.equals(StructureType.TOMBSTONE)) {
                lTile.setStructureType(StructureType.TREE);
                meadow = lTile.getMeadow();
                if (meadow == true) {
                    aGold += 2;
                } else {
                    aGold += 1;
                }
            }
        }
    }

    /**
     * 
     */
    public void updateUnits()  {
        Tile pTile;
        ActionType actionType;
        UnitType aUnitType;
        for (Unit lUnit : aUnits) {
            aUnitType = lUnit.getUnitType();
            if ((aUnitType.equals(UnitType.PEASANT))) {
                actionType = lUnit.getActionType();
                if ((actionType.equals(ActionType.CULTIVATING_END))) {
                    pTile = lUnit.getTile();
                    pTile.setHasMeadow(true);
                    lUnit.setActionType(actionType.READY); /*have to double check if this should be ready or moved*/
                }
                if ((actionType.equals(ActionType.BUILDINGROAD))) {
                    pTile = lUnit.getTile();
                    pTile.setStructureType(StructureType.ROAD);
                }
            }
        }
    }

    /**
     * Adds the specified amount of gold to the village
     * @param addGold
     */
    public void addGold(int addGold) 
    {
        aGold+=addGold; 
    }

    /**
     * Adds the specified amount of wood to the village
     * @param addWood
     */
    public void addWood(int addWood) 
    {
       aWood += addWood; 
    }

    
  /**
   * add a specified Tile to a village, then check if adding that tile caused 
   * two villages under the same player to become one Mega-Village
   * ONLY called either after removeTile from an enemy village, or on neutral land 
   * @param pTile
   */
    public void addTile(Tile pTile) {
    	ArrayList<Village> fusableVillages; 
    	aTiles.add(pTile);
    	fusableVillages = aMap.getNeighboringVillages(pTile); 
    	for (Village lVillage : fusableVillages)
    	{
    		if (aMap.canFuse(lVillage, this))
    		{
    			aMap.fuseVillages(lVillage, this);
    		}
    	}
    }

    public void removeTile(Tile t) {
        /* TODO: No message view defined */
    }

    public VillageType getVillageType() 
    {
    	return aVillageType; 
    }

    /**
     * checks if there's enough gold to pay all villagers 
     * if there is then village gold is reduced by the cost
     * otherwise a NotEnoughIncomeException is thrown and dealt with in the calling class 
     * @param goldCost
     * @throws NotEnoughIncomeException
     */
    public void tryPayingGold(int goldCost) throws NotEnoughIncomeException {
        if (aGold >= goldCost)
        {
        	aGold = aGold - goldCost; 
        }
        else 
        {
        	throw new NotEnoughIncomeException((aGold-goldCost)); 
        }
    }

    public Tile getCapital() {
        /* TODO: No message view defined */
        return null;
    }

    /**
     * getter for Village Gold
     * @return
     */
    public int getAGold() 
    {
        
        return aGold;
    }

    /**
     * getter for Village Wood
     * @return
     */
    public int getAWood() 
    {
      return aWood;
    }

    /**
     * This method should ONLY be called for getting equality
     * @return
     */
	public ArrayList<Tile> getTiles() 
	{  
		return (ArrayList<Tile>) aTiles.clone(); 
	}

	


    
    
}
