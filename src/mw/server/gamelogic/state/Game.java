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
import mw.server.gamelogic.logic.*;
import mw.server.gamelogic.partitioners.RandomMapPartitioner;
import mw.server.gamelogic.partitioners.SeaBorderPartitioner;
import mw.server.gamelogic.util.RandomColorGenerator;
import mw.shared.Coordinates;
import mw.util.CircularIterator;

/**
 * Game class definition.
 * @author emilysager
 */

@SuppressWarnings("serial")
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

		aMap = new GameMap(pWidth, pHeight);
		new RandomMapPartitioner(aMap).partition(availableColors); 
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
	public void upgradeUnit(Coordinates pCoordinates, UnitType pUnitType) 
	{
		Tile pTile = aMap.getTile(pCoordinates);
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
	public PossibleGameActions tileIsClicked(Coordinates pStartCoordinates)
	{
		Tile startTile = aMap.getTile(pStartCoordinates);
		VillageType VillageUpgradeType = GameLogic.getPossibleVillageUpgrades(startTile.getVillageType()); 
		Collection<Tile> ReachableTiles = new HashSet<Tile>();
		Collection<ActionType> UnitActions = new ArrayList<ActionType>();
		boolean canBuildWatchTower = GameLogic.canBuildWatchtower(startTile, this); 
		Collection<Tile> combinableUnitTiles = GameLogic.getCombinableUnitTiles(startTile, this); 

		//get possible reachable tiles and possible unit actions if the tile has a unit
		if (startTile.hasUnit())
		{
			Unit pUnit = startTile.getUnit();
			ReachableTiles = aMap.getPossibleMoves(startTile);
			UnitActions = Logic.getPossibleActions(pUnit, startTile);
		}
		Collection<UnitType> UnitUpgrade = GameLogic.getVillagerHireOrUpgradeTypes(startTile, this);
		Collection<Tile> hirableUnitTiles = wantToHireVillager(startTile);
		PossibleGameActions possible = new PossibleGameActions(ReachableTiles, UnitUpgrade, UnitActions, VillageUpgradeType, canBuildWatchTower, combinableUnitTiles, hirableUnitTiles);
		return possible; 
	}
	
	public Collection<Tile> wantToHireVillager(Tile pTile)
	{
		return UnitHireLogic.wantToHireUnit(pTile, this);
	}
 

	/**
	 * @param pTile
	 * @param pUnitType
	 * places the newly hired unit on the Tile
	 * includes upgradeVillager 
	 */
	public void hireVillager(Coordinates pCoordinates, UnitType pUnitType) throws NotEnoughIncomeException
	{
		Tile pTile = aMap.getTile(pCoordinates);
		//Decrement the Gold held by the hiring village
		int lHireCost = PriceCalculator.getUnitHireCost(pUnitType);
		int lWoodCost = 0; 
		if (pUnitType == UnitType.CANNON)
		{
			lWoodCost = 12; 
		}
		if (aMap.getVillage(pTile).getGold() >= lHireCost && aMap.getVillage(pTile).getWood() >= lWoodCost) 
		{
			aMap.getVillage(pTile).addOrSubtractGold(-lHireCost);	
			aMap.getVillage(pTile).addOrSubtractWood(-lWoodCost);
			Unit pUnit = new Unit(pUnitType); 
			pTile.setUnit(pUnit);
			pTile.notifyObservers();
			aMap.getVillage(pTile).getCapital().notifyObservers();
			
		}
		else 
		{
			throw new NotEnoughIncomeException("Hiring a " +  pUnitType.toString().toLowerCase() +" costs: " + lHireCost + " gold and " +lWoodCost +  " wood. " +
					"\n This village has " + 	aMap.getVillage(pTile).getGold() + " gold and " + aMap.getVillage(pTile).getWood() + " wood. "); 
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

	public Collection<Village> getVillages()
	{
		return aMap.getVillages();
	}

	/**
	 * Updates the state of the game at the beginning of a Unit's turn
	 */
	public void beginTurn() 
	{
		aCurrentPlayer = getNextPlayer();
		for (Village lVillage :aCurrentPlayer.getVillages()) 
		{
			lVillage.beginTurnUpdate();
		}
	}

	/**
	 * @return Player whose turn it now is
	 */
	private Player getNextPlayer()
	{
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
	public void moveUnit(Coordinates pStartCoordinates,Coordinates pDestinationCoordinates) 
	{
		//DON'T MAKE CHANGES TO THIS METHOD UNLESS YOU ARE EMILY 
		Tile startTile =  aMap.getTile(pStartCoordinates); 
		Tile pDestinationTile = aMap.getTile(pDestinationCoordinates);
		Unit crtUnit = startTile.getUnit();
		Color lColor = pDestinationTile.getColor();
		if (lColor == Color.NEUTRAL)
		{
			System.out.println("[Game] The unit is moving to neutral land.");
			Logic.checkFuse(startTile, pDestinationTile, this);
		}
		
		Logic.updateGameState(crtUnit, startTile, pDestinationTile, this, aMap);
	
		if (lColor == Color.NEUTRAL)
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


	public void takeoverEnemyTile(Tile startTile, Tile pDestinationTile)
	{
		System.out.println("[Game] Unit is attempting to take over enemy territory.");
		Village invadedVillage = getVillage(pDestinationTile);
		Village invadingVillage = getVillage(startTile);
		EnemyCaptureLogic.CaptureTile(invadingVillage, invadedVillage, pDestinationTile, this, aCurrentPlayer);  //capture the tile  and fuse the necessary villages
		aMap.deleteVillages(aPlayers, aCurrentPlayer);
	}

	/**
	 * builds a Watchtower
	 */
	public void buildWatchtower(Coordinates pCoordinates) 
	{
		Tile lTile = aMap.getTile(pCoordinates); 
		lTile.setStructureType(StructureType.WATCHTOWER); 
	}

	/**
	 * Puts a cannon on the tile with the parameter coordinates
	 * @param pCoordinates
	 */
	public void buyCannon(Coordinates pCoordinates)
	{
		Tile lTile = aMap.getTile(pCoordinates);
		lTile.setUnit(new Unit(UnitType.CANNON));
	}

	/**
	 * updates the action type of a unit on a given tile
	 * @param pTile
	 * @param pActionType
	 */
	public void setActionType(Coordinates pCoord, ActionType pActionType)
	{
		Tile pTile = aMap.getTile(pCoord);
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
	public void upgradeVillage(Coordinates pVillageCoord, VillageType pNewVillageType) throws CantUpgradeException, NotEnoughIncomeException 
	{
		Tile pTile = aMap.getTile(pVillageCoord);
		Village pVillage = getVillage(pTile);
		pVillage.upgrade(pNewVillageType);
		pTile.notifyObservers();
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
	 * @return
	 */
	public Player getCurrentPlayer() 
	{
		return aCurrentPlayer;
	}

	public Collection<Tile> getNeighbors(Tile pTile)
	{
		return aMap.getNeighbors(pTile); 
	}

	public void fuseVillages(Collection<Village> pToFuse,  Tile invadingCapital, Player pCurrentPlayer)
	{
		aMap.fuseVillages(pToFuse, invadingCapital, pCurrentPlayer);
	}


	//TODO move this into a logic class 
	public UnitType wantToCombineVillagers(Coordinates p1, Coordinates p2) 
	{
		UnitType combinedVillager = UnitType.NO_UNIT;
		Tile lTile1 = aMap.getTile(p1);
		Tile lTile2 = aMap.getTile(p2); 
		if (lTile1.hasUnit() && lTile2.hasUnit())
		{
			UnitType lUnitType1  = lTile1.getUnit().getUnitType(); 
			UnitType lUnitType2 = lTile2.getUnit().getUnitType(); 

			switch (lUnitType1)
			{
			case PEASANT: 
				switch (lUnitType2) 
				{
				case PEASANT:
					combinedVillager = UnitType.INFANTRY;
					break;
				case INFANTRY: 
					combinedVillager = UnitType.SOLDIER; 
					break;
				case SOLDIER: 
					combinedVillager = UnitType.KNIGHT;
					break;
				default:
					break;
				}
				break; 
			case INFANTRY: 
				switch (lUnitType2) 
				{
				case PEASANT:
					combinedVillager = UnitType.SOLDIER;
					break;
				case INFANTRY: 
					combinedVillager = UnitType.KNIGHT; 
					break;
				default:
					break;
				}
				break; 
			case SOLDIER: 
				switch (lUnitType2) 
				{
				case PEASANT:
					combinedVillager = UnitType.KNIGHT;
					break;
				default:
					break;
				}
			default:
				break;
			}
		}
		return combinedVillager; 
	}

	public void combineVillagers(Coordinates p1, Coordinates p2) {
		// TODO Auto-generated method stub
		
	}
}
