package mw.server.gamelogic;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;
import java.util.Set;
import java.util.Stack;

import mw.client.model.Account;
import mw.client.model.Coordinates;



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

    public void upgradeUnit(Unit pUnit, UnitType newType) 
    {
 
    }

    /**
     * 
     * @param pTile
     * @return
     * @throws CantUpgradeException
     * returns the list of units you can upgrade to, doesn't actually hire a villager 
     */
    public ArrayList<UnitType> wantToHireVillager(Tile pTile) throws CantUpgradeException
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
    			throw new CantUpgradeException("Cannot upgrade from Knight."); 
    		}
    	}
    	return rArray; 
    }
 
    public Collection<GameAction>  move(Tile startTile)
    {
    	aMap.getPossibleMoves(startTile)
    	Collection<Tile> possMoveTiles = PathFinder.getMovableTiles(startTile, aMap); 
    }
    public Collection<GameAction> getValidMoves(Tile startTile)
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

    
   
    public void moveUnit(Unit pUnit, Tile pDestinationTile) 
    {
    	//TODO 
    } 
    

    public void takeoverTile(Tile dest, Village invadingVillage) 
    {
      //TODO 
        
    }

    
    public void buildRoad(Unit u) 
    {
        UnitType unitType;
        unitType = u.getUnitType();
        if (unitType == UnitType.PEASANT) 
        {
            u.setActionType(ActionType.BUILDINGROAD);
        }
    }

    public void upgradeVillage(Village v, VillageType newType) 
    {
        try {
			v.upgradeVillage(newType);
		} catch (NotEnoughIncomeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    public void takeOverTile(Tile dest, Village invadingVillage)
    {
    	//TODO 
      
    }
    }

