/**
 * @author Charlie Bloomfield, Emily Sager
 * March 4, 2015
 */

package mw.server.gamelogic.controllers;

import java.util.ArrayList;
import java.util.Collection;

import mw.server.gamelogic.PossibleGameActions;
import mw.server.gamelogic.enums.ActionType;
import mw.server.gamelogic.enums.UnitType;
import mw.server.gamelogic.enums.VillageType;
import mw.server.gamelogic.exceptions.CantUpgradeException;
import mw.server.gamelogic.exceptions.NotEnoughIncomeException;
import mw.server.gamelogic.exceptions.TooManyPlayersException;
import mw.server.gamelogic.state.Game;
import mw.server.gamelogic.state.Player;
import mw.shared.Coordinates;


/**
 * Provides access to the networking code that sends commands to the server.
 */
public class GameController {

	/**
	 * @param the number of players to take part in the new game
	 * @return a new Game 
	 */
	public static Game newGame(int numPlayers) throws TooManyPlayersException 
	{
		int i = 0; 
		Collection<Player> gamePlayers = new ArrayList <Player> (); 
		while ( i < numPlayers)
		{
			Player lPlayer = new Player(); 
			gamePlayers.add(lPlayer);
			i++; 
		}
		Game crtGame = new Game(gamePlayers);
		return crtGame;
	}
	
	/**
	 * @param pGame
	 * @return the current player of pGame
	 */
	public static Player getCurrentPlayer(Game pGame)
	{
		return pGame.getCurrentPlayer();
	}
	
	/**
	 * 
	 * @param pGame
	 * @param pCoordinates
	 * @return
	 */
	public static PossibleGameActions getPossibleGameActions(Game pGame, Coordinates pCoordinates)
	{
		return  pGame.tileIsClicked(pCoordinates); 	
	}
	
	/**
	 * Ends the turn of the current Player in pGame. If this marks the end of a Round of play,
	 * a new round is created (by spawning trees and other miscellaneous shit). 
	 * @param pGame
	 * @return
	 */
	public static void endTurn(Game pGame)
	{
		pGame.endTurn();
	}

	/**
	 * 
	 * @param pGame
	 * @param pCoordinates
	 * @param upgradeType
	 * @throws NotEnoughIncomeException 
	 */
	public static void hireUnit(Game pGame, Coordinates pUnitCoordinates, UnitType upgradeType) throws NotEnoughIncomeException
	{
		pGame.hireVillager(pUnitCoordinates, upgradeType);
	}

	/**
	 * 
	 * @param pGame
	 * @param pSourceCoordinates
	 * @param pDestination
	 */
	public static void moveUnit(Game pGame, Coordinates pSourceCoordinates, Coordinates pDestinationCoordinates)
	{
		pGame.moveUnit(pSourceCoordinates, pDestinationCoordinates);
	}

	/**
	 * 
	 * @param pGame
	 * @param pUnitCoordinates
	 * @param pActionType
	 */
	public static void setActionType(Game pGame, Coordinates pUnitCoordinates, ActionType pActionType)
	{
		pGame.setActionType(pUnitCoordinates, pActionType);
	}

	/**
	 * 
	 * @param pGame
	 * @param pUnitCoordinatess
	 * @param upgradeType
	 */
	public static void upgradeUnit(Game pGame, Coordinates pUnitCoordinates, UnitType upgradeType)
	{
		pGame.upgradeUnit(pUnitCoordinates, upgradeType);
	}

	/**
	 * 
	 * @param pGame
	 * @param pVillageCoordinates
	 * @param pVillageType
	 * @throws NotEnoughIncomeException 
	 * @throws CantUpgradeException 
	 */
	public static void upgradeVillage(Game pGame, Coordinates pVillageCoordinates, VillageType pVillageType) throws CantUpgradeException, NotEnoughIncomeException
	{
		pGame.upgradeVillage(pVillageCoordinates, pVillageType);
	}
}
