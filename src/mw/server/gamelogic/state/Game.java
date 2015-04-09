package mw.server.gamelogic.state;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Stack;
import java.util.UUID;

import com.google.gson.GsonBuilder;

import mw.server.gamelogic.PossibleGameActions;
import mw.server.gamelogic.enums.ActionType;
import mw.server.gamelogic.enums.Color;
import mw.server.gamelogic.enums.PlayerState;
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
	private CircularIterator<Player> currentPlayerIterator;
	boolean isFirstTurn;
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
		new SeaBorderPartitioner(aMap).putSeaTilesOnTheMap();
		new RandomMapPartitioner(aMap).partition(availableColors); 
		aMap.randomlyGenerateTreesAndMeadows();
		assignVillageToPlayers();
		currentPlayerIterator = new CircularIterator<Player>(aPlayers);
		aCurrentPlayer = currentPlayerIterator.next(); 
		isFirstTurn = true;
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
	 * @return// TODO Auto-generated method stub
	 */
	public Tile [][] getGameTiles()
	{
		return aMap.getTiles(); 
	}

	/**
	 * @param pTile
	 * @param pUnitType
	 */
	public void upgradeUnit(Coordinates pCoordinates, UnitType pUnitType) throws NotEnoughIncomeException
	{

		Tile pTile = aMap.getTile(pCoordinates);
		UnitType pOldUnitType = UnitType.NO_UNIT;
		int upgradeCost = 0;
		if (pTile.hasUnit())
		{
			pOldUnitType = pTile.getUnit().getUnitType();
			upgradeCost = PriceCalculator.getUnitUpgradeCost(pOldUnitType, pUnitType);
			if (upgradeCost <=  getVillage(pTile).getGold())
			{
				pTile.setUnit(new Unit(pUnitType));
				pTile.notifyObservers();
			}
			else 
			{
				throw new NotEnoughIncomeException("To upgrade a " + pOldUnitType.toString().toLowerCase() + " to a " + pUnitType.toString().toLowerCase() 
						+ " costs " + upgradeCost + " This village only has " + getVillage(pTile).getGold());
			}
			// TODO Auto-generated method stub
		}
	}


	/**
	 * Generates all the possible moves when a given tile is clicked
	 * Moves include: Upgrading Villages, Moving Units, Upgrading Units, Units performing actions 
	 * @param startTile
	 * @return
	 */
	public PossibleGameActions tileIsClicked(Coordinates pStartCoordinates)
	{
		VillageType VillageUpgradeType = VillageType.NO_VILLAGE;
		Tile startTile = aMap.getTile(pStartCoordinates);
		if (!getVillage(startTile).alreadyUpgraded())
		{
			VillageUpgradeType = GameLogic.getPossibleVillageUpgrades(startTile.getVillageType()); 
		}
		Collection<Tile> firableTiles = new HashSet<Tile>();
		Collection<Tile> ReachableTiles = new HashSet<Tile>();
		Collection<ActionType> UnitActions = new ArrayList<ActionType>();
		boolean canBuildWatchTower = GameLogic.canBuildWatchtower(startTile, this); 
		Collection<Tile> combinableUnitTiles = GameLogic.getCombinableUnitTiles(startTile, this); 

		//get possible reachable tiles and possible unit actions if the tile has a unit
		if (startTile.hasUnit())
		{
			Unit pUnit = startTile.getUnit();
			if (pUnit.getActionType() == ActionType.READY) {
				if(pUnit.getUnitType()!=UnitType.CANNON)
				{
					ReachableTiles = aMap.getPossibleMoves(startTile);
				}
				else 
				{
					ReachableTiles = CannonLogic.getReachableTiles(startTile, this);
					firableTiles = CannonLogic.getFirableTiles(startTile, this);
				}
				UnitActions = Logic.getPossibleActions(pUnit, startTile);
			}
		}
		Collection<UnitType> UnitUpgrade = GameLogic.getVillagerHireOrUpgradeTypes(startTile, this);
		Collection<Tile> hirableUnitTiles = wantToHireVillager(startTile);
		PossibleGameActions possible = new PossibleGameActions(ReachableTiles, UnitUpgrade, UnitActions, VillageUpgradeType, canBuildWatchTower, combinableUnitTiles, hirableUnitTiles, firableTiles);
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
	 */// TODO Auto-generated method stub
	public void endTurn() 
	{
		System.out.println("[Game] Player is ending their turn");
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
		if(currentPlayerIterator.isAtBeginning())
		{
			return true;
		}
		return false;
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
			System.out.println("[Game] Updating state for the next player's turn.");
			lVillage.beginTurnUpdate(isFirstTurn);
		}
	}

	/**
	 * @return Player whose turn it now is
	 */// TODO Auto-generated method stub
	private Player getNextPlayer()
	{
		while (currentPlayerIterator.hasNext())
		{
			Player nextPlayer = currentPlayerIterator.next();
			if (nextPlayer.getPlayerState() == PlayerState.PLAYING || nextPlayer.getPlayerState() == PlayerState.WON)
			{
				return nextPlayer;
			}
		}
		throw new IllegalStateException("No more players left in the game!");
	}

	/**
	 * Generates trees on tiles 
	 */
	public void beginRound()
	{
		isFirstTurn = false;
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

		//get the unit and color of the start tile
		Unit crtUnit = startTile.getUnit();
		Color startColor = pDestinationTile.getColor();

		//get the color of the invaded tile
		Color lColor = pDestinationTile.getColor();
		if (lColor == Color.NEUTRAL)
		{
			System.out.println("[Game] The unit is moving to neutral land.");
			Logic.checkFuse(startTile, pDestinationTile, this);
		}
		if(lColor != startColor && lColor != Color.NEUTRAL)
		{
			takeoverEnemyTile(startTile, pDestinationTile);
		}
		else 
		{
			Logic.updateGameState(crtUnit, startTile, pDestinationTile, this, aMap);
		}
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
		System.out.println("[Game] The invaded village has initial size " + invadedVillage.getTiles().size());
		System.out.println("[Game] The invading village has initial size " + invadingVillage.getTiles().size());

		//capture the tile  and fuse the necessary villages
		EnemyCaptureLogic.CaptureTile(invadingVillage, invadedVillage, pDestinationTile, this, aCurrentPlayer); 

		System.out.println("[Game] The invaded village has final size " + invadedVillage.getTiles().size());
		System.out.println("[Game] The invading village has final size " + invadingVillage.getTiles().size());
		aMap.updateVillages(aPlayers, aCurrentPlayer, invadedVillage);
		EnemyCaptureLogic.move(startTile.getUnit(), pDestinationTile, invadingVillage);
		startTile.setUnit(null);
		pDestinationTile.setStructureType(StructureType.NO_STRUCT);
		pDestinationTile.setVillageType(VillageType.NO_VILLAGE);
		pDestinationTile.notifyObservers();
		Iterator<Player> aPlayersIterator = aPlayers.iterator();
		while (aPlayersIterator.hasNext())
		{
			Player loserCandidate = aPlayersIterator.next();
			if (loserCandidate.isEliminated())
			{
				loserCandidate.eliminate();
				loserCandidate.notifyObservers();
			}
		}
		if (aPlayers.size()==1)
		{
			Player winner = aPlayers.iterator().next();
			winner.win();
		}
	}

	/**
	 * builds a Watchtower
	 * @throws NotEnoughIncomeException 
	 */
	public void buildWatchtower(Coordinates pCoordinates) throws NotEnoughIncomeException 
	{
		Tile lTile = aMap.getTile(pCoordinates); 
		if (aMap.getVillage(lTile).getWood() > 5)
		{
			lTile.setStructureType(StructureType.WATCHTOWER); 
			lTile.notifyObservers();
			aMap.getVillage(lTile).addOrSubtractWood(-5);
			aMap.getVillage(lTile).getCapital().notifyObservers();
		}
		else 
		{
			throw new NotEnoughIncomeException("Building a watchtower requires 5 wood.  This village has " + aMap.getVillage(lTile).getWood());
		}
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

			try {
				combinedVillager = UnitHireLogic.unitCombinationResult(lUnitType1, lUnitType2);
			}
			catch (IllegalArgumentException e) {
				combinedVillager = UnitType.NO_UNIT;
			}
		}
		return combinedVillager; 
	}

	public void combineVillagers(Coordinates p1, Coordinates p2) {
		Tile srcTile = aMap.getTile(p1);
		Tile destTile = aMap.getTile(p2);

		Unit srcUnit = srcTile.getUnit();
		Unit destUnit = destTile.getUnit();

		UnitType resultingType = UnitHireLogic.unitCombinationResult(srcUnit.getUnitType(), destUnit.getUnitType());
		srcTile.setUnit(null);
		destUnit.setUnitType(resultingType);
		destUnit.setActionType(ActionType.MOVED);

		srcTile.notifyObservers();
		destTile.notifyChanged();
	}

	public void fireCannon(Coordinates pCannonCoordinates, Coordinates pFirableCoord) 
	{
		Tile pCannonTile = getTile(pCannonCoordinates);
		Tile pFirableTile = getTile(pFirableCoord);
		StructureType firableStructureType = pFirableTile.getStructureType();
		switch (firableStructureType)
		{
		case VILLAGE_CAPITAL: 
			Village destroyedVillage = getVillage(pFirableTile);
			destroyedVillage.incrementCannonHits();
			if (destroyedVillage.isDestroyedByCannon())
			{
				destroyedVillage.addOrSubtractGold(-destroyedVillage.getGold());
				destroyedVillage.addOrSubtractWood(-destroyedVillage.getWood());
				destroyedVillage.setRandomCapital();
				destroyedVillage.resetCannonHits();
			}
		default: 
			pFirableTile.setStructureType(StructureType.NO_STRUCT);
		}
		pFirableTile.setMeadow(false);
		pFirableTile.setUnit(null);
		pFirableTile.notifyObservers();

		if (pCannonTile.hasUnit())
		{
			pCannonTile.getUnit().setActionType(ActionType.MOVED);
		}
	}


}
