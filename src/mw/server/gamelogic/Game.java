package mw.server.gamelogic;

import java.util.ArrayList;
import java.util.Random;
import java.util.Set;
import java.util.Stack;



/**
 * Game class definition.
 * @author emilysager
 */
public class Game extends RandomColorGenerator {
    
    private ArrayList<Player> aPlayers;
    private  GameMap aMap;  
    private Player aCurrentPlayer;

/**
 * 
 * @param pPlayers
 * @param mapID
 * @param colors
 * Game Constructor -- either randomly generates a new map or it Queries the database for a give map ID 
 * @throws TooManyPlayersException 
 */
public Game (ArrayList<Player> pPlayers, int mapID) throws TooManyPlayersException {
    aPlayers  = pPlayers;
    
     Stack <Color >myColors = new Stack <Color>(); 
     myColors.push(Color.BLUE); 
     myColors.push(Color.GREEN);
     myColors.push(Color.RED); 
     myColors.push(Color.YELLOW); 
     
	for (Player lPlayer : aPlayers) 
	{
		if (!myColors.isEmpty())
		{
          lPlayer.assignColor(myColors.pop());
		}
		else
		{
			throw new TooManyPlayersException(); 
		}
    }
        if (mapID == 0) {
            aMap = new GameMap(30, 10); 
        } 
        else { //Query our database of maps 
           
            aMap = MapBase.getMap(mapID);
        }
        aMap.partition(); 
    }

    public void upgradeUnit(Unit pUnit, UnitType newType) {
        boolean success;
        int upgradeCost;
        Village villageRuling;
        Tile tile;
        UnitType unitType;
        unitType = pUnit.getUnitType();
        upgradeCost = PaymentManager.upgradeCost(unitType, newType);
        tile = pUnit.getTile();
        villageRuling = aCurrentPlayer.getVillageRuling(tile);
        success = villageRuling.tryPayingGold(upgradeCost);
    }

    /**
     * 
     * @param pTile
     * @return
     * @throws UnitCantUpgradeException
     * returns the list of units you can upgrade to, doesn't actually hire a villager 
     */
    public ArrayList<UnitType> wantToHireVillager(Tile pTile) throws UnitCantUpgradeException
    {
    	ArrayList<UnitType> rArray = new ArrayList<UnitType>();
    	if (pTile.getUnit().equals(null))
    	{
    		rArray.add(UnitType.INFANTRY); 
    		rArray.add(UnitType.KNIGHT); 
    		rArray.add(UnitType.PEASANT); 
    		rArray.add(UnitType.SOLDIER); 
    				
    	}
    	else 
    	{
    		if (pTile.getUnit().getUnitType().equals(UnitType.PEASANT))
    		{
    			rArray.add(UnitType.INFANTRY);
    			rArray.add(UnitType.SOLDIER); 
        		rArray.add(UnitType.KNIGHT);
    		}
    		if (pTile.getUnit().getUnitType().equals(UnitType.INFANTRY))
    		{
    			rArray.add(UnitType.SOLDIER); 
        		rArray.add(UnitType.KNIGHT);
    		}
    		if (pTile.getUnit().getUnitType().equals(UnitType.SOLDIER))
    		{
    			rArray.add(UnitType.KNIGHT); 
    		}
    		if (pTile.getUnit().getUnitType().equals(UnitType.KNIGHT)) 
    		{
    			throw new UnitCantUpgradeException("Cannot upgrade from Knight."); 
    		}
    	}
    	return rArray; 
    }
 
    public Set<Tile> move(Tile startTile)
    {
    	
    }
    public ArrayList<Move> getValidMoves()
    {
    	return null; 
    }
/**
 * 
 * @param pTile
 * @param pUnitType
 * places the newly hired unit on the Tile
 * includes upgradeVillager 
 * 
 */
    //TODO add gold deduction from village
public void hireVillager(Tile pTile, UnitType pUnitType)
    {
    	Unit pUnit = new Unit(pUnitType); 
    	pTile.setUnit(pUnit);
    }
    public void beginTurn() {
        ArrayList<Village> aCrtVillages;
        aCrtVillages = aCurrentPlayer.getVillages();
        for (Village lVillage : aCrtVillages) {
            lVillage.updateTiles();
            lVillage.updateUnits();
        }
    }
    /**
     * Generates trees on tiles 
     */
    public void beginRound()
    {
    	//TODO 
    }

    
    //TODO -- Check if the unit is a knight, and act accordingly
    public void moveUnit(Unit pUnit, Tile pDestinationTile) {
        Tile crtTile;
        boolean isPath;
        Village crtVillage;
        Village villageDest;
        StructureType structureType;
        crtTile = pUnit.getTile();
        isPath = aMap.getPath(crtTile, pDestinationTile);
        structureType = pDestinationTile.getStructureType();
        
        if (isPath) 
        {
        	if (structureType==StructureType.TOMBSTONE) {
        		pDestinationTile.setStructureType(StructureType.NO_STRUCT); 
        		pUnit.setTile(pDestinationTile);
        	}
        	crtVillage = crtTile.getVillage();
        	if (structureType==StructureType.TREE) {
        		pDestinationTile.setStructureType(StructureType.NO_STRUCT);
        		pUnit.setTile(pDestinationTile);
        		crtVillage.addWood(1);
        	}
        	villageDest = pDestinationTile.getVillage();
        	if (villageDest==null) { // if the tile we want to go to is neutral land
        		crtVillage.addTile(pDestinationTile);
        	} 
        }
    }

    public void takeoverTile(Tile dest, Village invadingVillage) {
        Village invadedVillage;
        boolean canFuse;
        int aWood;
        StructureType structureType;
        int aGold;
        invadedVillage = dest.getVillage();
        structureType = dest.getStructureType();
        if (invadedVillage.getCapital().equals(dest)) {
            aGold = invadedVillage.getAGold();
            invadingVillage.addGold(aGold);
            aWood = invadedVillage.getAWood();
            invadingVillage.addWood(aWood);
            dest.setStructureType(StructureType.NO_STRUCT);  //Structure Type should be none because the invading village will already have a Village
        }
        dest.setVillage(invadingVillage); //ugh the set village method is gonna suck 
        invadedVillage.removeTile(dest);
        invadingVillage.addTile(dest);
        
    }

    public void buildRoad(Unit u) {
        UnitType unitType;
        unitType = u.getUnitType();
        if (unitType == UnitType.PEASANT) {
            u.setActionType(ActionType.BUILDINGROAD);
        }
    }

    public void upgradeVillage(Village v, VillageType newType) {
        try {
			v.upgradeVillage(newType);
		} catch (NotEnoughIncomeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    public void takeOverTile(Tile dest, Village invadingVillage) {
        int aWood;
        StructureType structureType;
        boolean canFuse;
        Village invadedVillage;
        Tile capital;
        int aGold;
        invadedVillage = dest.getVillage();
        structureType = dest.getStructureType();
        capital = invadedVillage.getCapital();
        if (dest.equals(capital)) {
            aGold = invadedVillage.getAGold();
            invadingVillage.addGold(aGold);
            aWood = invadedVillage.getAWood();
            invadingVillage.addWood(aWood);
            dest.setStructureType(StructureType.NO_STRUCT); //should this be no struct? The invading village already has a structure type
        }
        dest.setVillage(invadingVillage);
        invadingVillage.addTile(dest);
      
        }
    }

