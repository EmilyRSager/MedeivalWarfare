package mw.server.gamelogic.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Stack;

import mw.server.gamelogic.Logic;
import mw.server.gamelogic.exceptions.CantUpgradeException;
import mw.server.gamelogic.exceptions.NotEnoughIncomeException;
import mw.server.gamelogic.exceptions.TooManyPlayersException;
import mw.server.gamelogic.partitioners.RandomMapPartitioner;
import mw.util.CircularIterator;

/**
 * Game class definition.
 * @author emilysager
 */
public class Game extends RandomColorGenerator implements Serializable{
	private static final int DEFAULT_WIDTH = 18;
	private static final int DEFAULT_HEIGHT = 18;
	private Collection<Player> aPlayers;
	private GameMap aGameMap;  
	private Player aCurrentPlayer;
	CircularIterator<Player> crtIterator;

	/**
	 * Overloaded constructor passes default dimensions to main constructor
	 * @param pPlayers
	 * @throws TooManyPlayersException
	 */
	public Game(Collection<Player> pPlayers) throws TooManyPlayersException {
		this(pPlayers, DEFAULT_WIDTH, DEFAULT_HEIGHT);
	}

	/**
	 * @param pPlayers
	 * @param pWidth
	 * @param pHeight
	 * @throws TooManyPlayersException 
	 */
	public Game (Collection<Player> pPlayers, int pWidth, int pHeight) throws TooManyPlayersException {
		aPlayers  = pPlayers;
		Stack <Color> myColors = new Stack <Color>(); 
		myColors.push(Color.BLUE); 
		myColors.push(Color.GREEN);
		myColors.push(Color.RED); 
		myColors.push(Color.YELLOW); 

		Collection <Color> availableColors = new ArrayList<Color>(); 
		for (Player lPlayer : aPlayers) //don't need to randomly assign colors because players come in randomly
		{
			if (!myColors.isEmpty())
			{
				lPlayer.assignColor(myColors.peek());

				availableColors.add(myColors.pop());
			}
			else
			{
				throw new TooManyPlayersException(); 
			}
		}

		aGameMap = new GameMap(pWidth, pHeight, availableColors); 
		new RandomMapPartitioner().partition(aGameMap);
		assignVillagesToPlayers();

		crtIterator = new CircularIterator<Player>(pPlayers);
		aCurrentPlayer = crtIterator.next(); 
	}

	/**
	 * 
	 */
	private void assignVillagesToPlayers(){
		//assign color to each village that was created
		Collection<Village> lVillages = aGameMap.getVillages();
		for(Player lPlayer : aPlayers){
			Color lColor = lPlayer.getPlayerColor();
			for(Village lVillage : lVillages){
				if(lVillage.getColor() == lColor){
					lPlayer.addVillage(lVillage);
				}
			}
		}
	}

	/**
	 * returns the tile in a game with specified coordinates
	 * @param pCoord
	 * @return
	 */
	public Tile getTile(Coordinates pCoord)
	{
		return aGameMap.getTile(pCoord);
	}

	/**
	 * Returns all the tiles in the game in a 2D array 
	 * [i][j] indices correspond with x y coordinates
	 * @return
	 */
	public Tile [][] getGameTiles()
	{
		return aGameMap.getTiles(); 
	}

	/**
	 * @param pTile
	 * @param pUnitType
	 */
	public void upgradeUnit(Tile pTile, UnitType pUnitType) 
	{
		Unit pUnit = pTile.getUnit();
		if (pUnit!=null)
		{
			ArrayList<UnitType> possUpgrades = wantToHireVillager(pTile);
			if (possUpgrades.contains(pUnitType))
			{
				pUnit.setUnitType(pUnitType);
				pTile.setUnit(pUnit);
				pTile.notifyObservers(); 
			}
		}
	}

	/**
	 * @param pTile
	 * @return
	 * @throws CantUpgradeException
	 * returns the list of units you can upgrade to, doesn't actually hire a villager 
	 */
	public ArrayList<UnitType> wantToHireVillager(Tile pTile) 
	{
		ArrayList<UnitType> rArray = new ArrayList<UnitType>();

		if (pTile.getUnit()==(null))
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
	 * Generates all the possible moves when a given tile is clicked
	 * Moves include: Upgrading Villages, Moving Units, Upgrading Units, Units performing actions 
	 * @param startTile
	 * @return
	 */
	public PossibleGameActions tileIsClicked(Tile startTile)
	{
		VillageType startVillageType = startTile.getVillageType(); 
		VillageType possVillageUpgradeType = VillageType.NO_VILLAGE; 
		Unit pUnit = startTile.getUnit();

		Collection<Tile> possMoveTiles = new HashSet<Tile>();

		//get possible reachable tiles if the tile has a unit
		if(pUnit != null){
			possMoveTiles = aGameMap.getPossibleMoves(startTile);
		}

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

		PossibleGameActions possible = new PossibleGameActions(possMoveTiles, possUnitUpgrade, possActions, possVillageUpgradeType);
		return possible; 
	}

	/**
	 * @param pTile
	 * @param pUnitType
	 * places the newly hired unit on the Tile
	 * includes upgradeVillager 
	 */
	public void hireVillager(Tile pTile, UnitType pUnitType)
	{
		if (pTile.getUnit()==null)
		{
			if (pTile.getStructureType()!= StructureType.TREE && pTile.getStructureType()!=StructureType.VILLAGE_CAPITAL
					&& pTile.getStructureType()!=StructureType.TOMBSTONE && pTile.getStructureType()!=StructureType.WATCHTOWER)
			{
				//Decrement the Gold held by the hiring village
				int lHireCost;
				lHireCost = PriceCalculator.getUnitHireCost(pUnitType);
				aGameMap.getVillage(pTile).addOrSubtractGold(-lHireCost);

				//place a new unit on pTile
				Unit pUnit = new Unit(pUnitType); 
				pTile.setUnit(pUnit);
				pTile.notifyObservers();
			}
		}

	}

	/**
	 * Informs the Game that the current Player is ending its turn. 
	 */
	public void endTurn() 
	{
		if (currentRoundIsOver())
		{
			System.out.println("[Server] Round is over.");
			beginRound();
		}

		beginTurn();
	}

	/**
	 * @return true if the current round of Game play is over, false otherwise
	 */
	private boolean currentRoundIsOver(){
		return crtIterator.isAtBeginning();
	}

	/**
	 * Updates the state of the game at the beginning of a Unit's turn
	 */
	public void beginTurn() 
	{
		aCurrentPlayer = getNextPlayer();
		//TODO villages are never passed to Players

		Collection<Village> aCrtVillages;
		aCrtVillages = aCurrentPlayer.getVillages();

		for (Village lVillage : aCrtVillages) {
			lVillage.updateUnits();
			lVillage.updateTiles();
		}
	}

	/**
	 * @return Player whose turn it now is
	 */
	private Player getNextPlayer(){
		return crtIterator.next();
	}

	/**
	 * Generates trees on tiles 
	 */
	public void beginRound()
	{
		aGameMap.generateTrees();
	}

	/**
	 * Moves a unit from a start to destination tile
	 * Game State changes are calculated in the Logic class
	 * @param startTile
	 * @param pDestinationTile
	 */
	public void moveUnit(Tile startTile, Tile pDestinationTile) 
	{
		if (startTile.equals(pDestinationTile))
		{
			return;
		}
		Unit crtUnit = startTile.getUnit(); 
		if (crtUnit == null) 
		{
			return; 
		}
		else    		
		{
			if (Logic.updateGameState(crtUnit, startTile, pDestinationTile, this, aGameMap)) 
			{
				crtUnit.setActionType(ActionType.MOVED);
			}
		}

		startTile.notifyObservers();
		pDestinationTile.notifyObservers();
	} 


	/**
	 * A Unit moves to land not under its control 
	 * @param startTile
	 * @param pDestinationTile
	 */
	public void takeoverTile(Tile startTile, Tile pDestinationTile) 
	{
		pDestinationTile.setColor(startTile.getColor());
		Village lCapturingVillage = aGameMap.getVillage(startTile);
		lCapturingVillage.addTile(pDestinationTile);
	}

	/**
	 * updates the action type of a unit on a given tile
	 * @param pTile
	 * @param pActionType
	 */
	public void setActionType(Tile pTile, ActionType pActionType)
	{
		Unit pUnit = pTile.getUnit(); 
		if (pUnit != null)
		{
			if (Logic.getPossibleActions(pUnit, pTile).contains(pActionType))
			{
				pUnit.setActionType(pActionType);
			}
		}
	}

	/**
	 * Upgrades the given village from its current type to the newType
	 * @param pVillage
	 * @param pNewType
	 */
	public void upgradeVillage(Village pVillage, VillageType pNewVillageType) 
	{
		try {
			pVillage.upgrade(pNewVillageType);
		} catch (NotEnoughIncomeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @return
	 */
	public Collection<Player> getPlayers()
	{
		return aPlayers; 
	}

	/**
	 * @param pTile
	 * @return
	 */
	public Village getVillage(Tile pTile)
	{
		return aGameMap.getVillage(pTile);
	}

	public Player getCurrentPlayer() 
	{
		return aCurrentPlayer;
	}
}

