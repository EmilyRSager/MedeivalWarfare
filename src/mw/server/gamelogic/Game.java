package mw.server.gamelogic;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Stack;

/**
 * Game class definition.
 * @author emilysager
 */
public class Game extends RandomColorGenerator {

	private Collection<Player> aPlayers;
	private GameMap aMap;  
	private Player aCurrentPlayer;

	/**
	 * 
	 * @param pPlayers
	 * @param mapID
	 * @param colors
	 * Game Constructor -- either randomly generates a new map or it Queries the database for a give map ID 
	 * @throws TooManyPlayersException 
	 */
	public Game (Collection<Player> pPlayers, int mapID) throws TooManyPlayersException {


		aPlayers  = pPlayers;
		Stack <Color> myColors = new Stack <Color>(); 
		myColors.push(Color.BLUE); 
		myColors.push(Color.GREEN);
		myColors.push(Color.RED); 
		myColors.push(Color.YELLOW); 

		for (Player lPlayer : aPlayers) //don't need to randomly assign colors because players come in randomly
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

	public Tile [][] getGameTiles()
	{
		return aMap.getObservables(); 
	}

	public void upgradeUnit(Tile pTile) 
	{
		Unit pUnit = pTile.getUnit();
		ArrayList<UnitType> possUpgrades = wantToHireVillager(pTile);
		//TODO
		
		
	}

	/**
	 * 
	 * @param pTile
	 * @return
	 * @throws CantUpgradeException
	 * returns the list of units you can upgrade to, doesn't actually hire a villager 
	 */
	public ArrayList<UnitType> wantToHireVillager(Tile pTile) 
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
				return null; 
			}
		}
		return rArray; 
	}

	/**
	 * 
	 * @param startTile
	 * @return
	 */
	public CollectionOfPossibleActions tileIsClicked(Tile startTile)
	{
		VillageType startVillageType = startTile.getVillageType(); 
		VillageType possVillageUpgradeType = VillageType.NO_VILLAGE; 
		Unit pUnit = startTile.getUnit(); 
		Collection<Tile> possMoveTiles = aMap.getPossibleMoves(startTile);
		Collection<UnitType> possUnitUpgrade = wantToHireVillager(startTile);
		Collection<ActionType> possActions = new ArrayList<ActionType>();


		switch (startVillageType)
		{
		case HOVEL:
			possVillageUpgradeType = VillageType.TOWN;
			break;
		case TOWN: 
			possVillageUpgradeType = VillageType.FORT;
			break; 
		case FORT: 
			possVillageUpgradeType = null;  
			break;
		default:
			possVillageUpgradeType = VillageType.NO_VILLAGE; 
			break;
		}


		if (pUnit!=null)
		{
			possActions = Logic.getPossibleActions(pUnit, startTile);
		}

		CollectionOfPossibleActions possible = new CollectionOfPossibleActions(possMoveTiles, possUnitUpgrade, possActions, possVillageUpgradeType);
		return possible; 




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
		pTile.notifyObservers(); 
	}

	public void beginTurn() 
	{
		Collection<Village> aCrtVillages;
		aCrtVillages = aCurrentPlayer.getVillages();
		for (Village lVillage : aCrtVillages) {
			lVillage.updateUnits();
			lVillage.updateTiles();
		}
	}
	/**
	 * Generates trees on tiles 
	 */
	 public void beginRound()
	 {
		 //TODO ABISHEK PLEASE DO THIS
	 }



	 public void moveUnit(Tile startTile, Tile pDestinationTile) 
	 {
		 Unit crtUnit = startTile.getUnit(); 
		 if (crtUnit == null) 
		 {
			 return; 
		 }
		 else    		
		 {
			 Logic.updateGameState(crtUnit, startTile, pDestinationTile, this, aMap);  
		 }

	 } 


	 public void takeoverTile(Tile startTile, Tile pDestinationTile) 
	 {
		 //TODO: for the demo at least have the neutral tile takeover ready 

	 }


	 public void buildRoad(Tile pTile) 
	 {
		 Unit u = pTile.getUnit();  
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
	 public GameMap getGameMap()
	 {
		 return aMap; 
	 }
	 public Collection<Player> getPlayers() 
	 {
		 return aPlayers; 
	 }
}

