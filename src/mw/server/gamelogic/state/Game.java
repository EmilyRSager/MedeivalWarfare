package mw.server.gamelogic.state;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Stack;

import mw.server.gamelogic.PossibleGameActions;
import mw.server.gamelogic.enums.ActionType;
import mw.server.gamelogic.enums.Color;
import mw.server.gamelogic.enums.StructureType;
import mw.server.gamelogic.enums.UnitType;
import mw.server.gamelogic.enums.VillageType;
import mw.server.gamelogic.exceptions.CantUpgradeException;
import mw.server.gamelogic.exceptions.NotEnoughIncomeException;
import mw.server.gamelogic.exceptions.TooManyPlayersException;
import mw.server.gamelogic.logic.Logic;
import mw.server.gamelogic.logic.*;
import mw.server.gamelogic.logic.PriceCalculator;
import mw.server.gamelogic.util.RandomColorGenerator;
import mw.util.CircularIterator;

/**
 * Game class definition.
 * @author emilysager
 */
public class Game extends RandomColorGenerator implements Serializable{
	private static final int DEFAULT_WIDTH = 18;
	private static final int DEFAULT_HEIGHT = 18;
	private Collection<Player> aPlayers;
	private GameMap aMap;  
	private Player aCurrentPlayer;
	CircularIterator<Player> crtIterator;

	/**
	 * Overloaded constructor passes default dimensions to main constructor
	 * @param pPlayers
	 * @throws TooManyPlayersException
	 */
	public Game(Collection<Player> pPlayers) throws TooManyPlayersException 
	{
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
		for (Player lPlayer : aPlayers) 
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

		aMap = new GameMap(pWidth, pHeight, availableColors); 
		aMap.partition();
		assignVillageToPlayers();
		crtIterator = new CircularIterator<Player>(pPlayers);
		aCurrentPlayer = crtIterator.next(); 
	}

	/**
	 * 
	 */
	private void assignVillageToPlayers(){
		Collection<Village> lVillages = aMap.getVillages();
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
	 *  returns the tile in a game with specified coordinates
	 * @param pCoord
	 * @return
	 */
	public Tile getTile(Coordinates pCoord)
	{
		return aMap.getTile(pCoord);
	}

	/**
	 * Returns all the tiles in the game in a 2D array 
	 * [i][j] indices correspond with x y coordinates
	 * @return
	 */
	public Tile [][] getGameTiles()
	{
		return aMap.getTiles(); 
	}

	/**
	 * @param pTile
	 * @param pUnitType
	 */
	public void upgradeUnit(Tile pTile, UnitType pUnitType) 
	{
		Unit pUnit = pTile.getUnit();
		pUnit.setUnitType(pUnitType);
		pTile.notifyObservers();
	}


	/**
	 * Generates all the possible moves when a given tile is clicked
	 * Moves include: Upgrading Villages, Moving Units, Upgrading Units, Units performing actions 
	 * @param startTile
	 * @return
	 */
	public PossibleGameActions tileIsClicked(Tile startTile)
	{
		VillageType VillageUpgradeType = GameLogic.getPossibleVillageUpgrades(startTile.getVillageType()); 
		Collection<Tile> ReachableTiles = new HashSet<Tile>();
		Collection<ActionType> UnitActions = new ArrayList<ActionType>();
		
		//get possible reachable tiles and possible unit actions if the tile has a unit
		if(startTile.hasUnit())
		{
			Unit pUnit = startTile.getUnit();
		ReachableTiles = aMap.getPossibleMoves(startTile);
			UnitActions = Logic.getPossibleActions(pUnit, startTile);
		}
		Collection<UnitType> UnitUpgrade = GameLogic.wantToHireVillager(startTile);
		PossibleGameActions possible = new PossibleGameActions(ReachableTiles, UnitUpgrade, UnitActions, VillageUpgradeType);
		return possible; 
	}

	/**
	 * @param pTile
	 * @param pUnitType
	 * places the newly hired unit on the Tile
	 * includes upgradeVillager 
	 */
	public void hireVillager(Coordinates pCoordinates, UnitType pUnitType)
	{
		Tile pTile = aMap.getTile(pCoordinates);
		if (pTile.getUnit()==null)
		{
			if (pTile.getStructureType()!= StructureType.TREE && pTile.getStructureType()!=StructureType.VILLAGE_CAPITAL
					&& pTile.getStructureType()!=StructureType.TOMBSTONE && pTile.getStructureType()!=StructureType.WATCHTOWER)
			{
				//Decrement the Gold held by the hiring village
				int lHireCost;
				lHireCost = PriceCalculator.getUnitHireCost(pUnitType);
				aMap.getVillage(pTile).addOrSubtractGold(-lHireCost);

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
			beginRound();
		}
		beginTurn();
	}

	/**
	 * @return true if the current round of Game play is over, false otherwise
	 */
	private boolean currentRoundIsOver()
	{
		return crtIterator.isAtBeginning();
	}

	/**
	 * Updates the state of the game at the beginning of a Unit's turn
	 */
	public void beginTurn() 
	{
		aCurrentPlayer = getNextPlayer();

		for (Village lVillage :aCurrentPlayer.getVillages()) {
			lVillage.beginTurnUpdate();
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
		aMap.generateTrees();
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
			//TODO move this into initial verification
			return;
		}
		Unit crtUnit = startTile.getUnit();
		if (Logic.updateGameState(crtUnit, startTile, pDestinationTile, this, aMap)) 
		{
			crtUnit.setActionType(ActionType.MOVED);
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
		Village lCapturingVillage = aMap.getVillage(startTile);
		lCapturingVillage.addTile(pDestinationTile);
		//TODO -- recalculate village 
		//aMap.recalculateVillages();
		//moveUnit(startTile, pDestinationTile);			
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
	 * @throws CantUpgradeException 
	 */
	public void upgradeVillage(Village pVillage, VillageType pNewVillageType) throws CantUpgradeException, NotEnoughIncomeException 
	{
		pVillage.upgrade(pNewVillageType);
	}

	/**
	 * Gets the game map for the current game
	 * @return
	 */
	public GameMap getGameMap()
	{
		return aMap;
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
		return aMap.getVillage(pTile);
	}

	/**
	 * 
	 * @return
	 */
	public Player getCurrentPlayer() 
	{
		return aCurrentPlayer;
	}
}

