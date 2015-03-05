package mw.server.gamelogic;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

import javax.annotation.Generated;



/**
 * Village class definition.
 * @author emilysager, Abhishek Gupta
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
		aGold = 0; 
		aWood = 0; 

	}
	public Village(Set <GraphNode> villageSet, int pGold, int pWood)
	{
		aVillageNodes = villageSet; 
		aGold = pGold;
		aWood = pWood;
	}
	
	private void generateGold()
	{
		int addGold = 0;  
		for (GraphNode lGraphNode: aVillageNodes)
		{ 
			addGold +=Logic.getGoldGenerated(lGraphNode);  
		}
		aGold += addGold; 
	}
	public void upgradeVillage(VillageType pVillageType) throws NotEnoughIncomeException {
		int upgradeCost = 0;
		try {
			upgradeCost = PriceCalculator.getUpgradePrice(pVillageType);
		} catch (CantUpgradeException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

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
			throw new NotEnoughIncomeException(aWood - upgradeCost); 
		}
	}

	/**
	 * goes through the village and adds gold to the village based on the number of meadows/tiles 
	 * replaces any Tombstones with trees
	 */
	public void updateTiles() 
	{
		for (GraphNode lGraphNode : aVillageNodes) 
		{
			Logic.clearTombstone(lGraphNode);
		}
		generateGold();
	}


	/**
	 * produces meadow/road on the tile and resets the unit to be free 
	 * assuming that the free state of the unit is "READY"
	 * this function will do the update for all the units that are on tiles belonging to the village
	 */
	public void updateUnits()  
	{
		for(GraphNode lNode: aVillageNodes){
			Tile lTile = lNode.getTile();
			Unit lUnit = lTile.getUnit();
			ActionType lActionType = lUnit.getActionType();
			
			if (lActionType.equals(ActionType.CULTIVATING_END)) {
				lTile.setHasMeadow(true);
				lUnit.setActionType(ActionType.READY);
			}
			
		}
	}



	/**
	 * Adds the specified amount of gold to the village, maybe used in the context of takeOver village
	 * @param addGold
	 */
	public void addGold(int addGold) 
	{
		aGold += addGold; 
	}

	/**
	 * Adds the specified amount of wood to the village, maybe used in the context of takeOver village
	 * @param addWood
	 */
	public void addWood(int addWood) 
	{
		aWood += addWood; 
	}


	/**
	 * add a specified Tile to a village, then check if adding that tile caused 
	 * two villages under the same player to become one Mega-Village
	 * @precondition ONLY called either after removeTile from an enemy village, or on neutral land 
	 * @param pTile
	 */
	public void addTile(GraphNode pGraphNode) 
	{
		//TODO: needs to be implemented
		//some type of search here to see if the village has fused
	}
	
	/**
	 * remove a tile from the village, would also take care that if this tile is removed causing a split into
	 * smaller villages then it is handled here 
	 * @param t = tile to be removed
	 */
	public void removeTile(Tile t) 
	{
		/* TODO: needs to be implemented */
	}

	public VillageType getVillageType() 
	{
		return aVillageType; 
	}

	/**
	 * checks if there's enough gold to pay all villagers 
	 * if there is then village gold is reduced by the cost
	 * otherwise a NotEnoughIncomeException is thrown and dealt with in the calling class 
	 * 
	 * check in price_calculator class for an upkeep cost calculator method instead of using this
	 * 
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
}
