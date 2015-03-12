/**
 * @author Charlie Bloomfield, Emily Sager
 * March 4, 2015
 */

package mw.server.gamelogic.controllers;

import java.util.ArrayList;
import java.util.Collection;

import mw.server.gamelogic.exceptions.TooManyPlayersException;
import mw.server.gamelogic.model.ActionType;
import mw.server.gamelogic.model.Coordinates;
import mw.server.gamelogic.model.Game;
import mw.server.gamelogic.model.Player;
import mw.server.gamelogic.model.PossibleGameActions;
import mw.server.gamelogic.model.Tile;
import mw.server.gamelogic.model.UnitType;
import mw.server.gamelogic.model.Village;
import mw.server.gamelogic.model.VillageType;

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
		Player lCurrentPlayer = pGame.getCurrentPlayer();
		return lCurrentPlayer;
	}
	

	/**
	 * Returns all the possible moves or upgrades a player can make given a certain tile
	 * @param pGame
	 * @param pRow
	 * @param pColumn
	 * @return
	 */
	public static PossibleGameActions getPossibleGameActions(Game pGame, int pRow, int pColumn)
	{
		Coordinates mappingCoordinates = new Coordinates(pRow, pColumn);
		Tile clicked = pGame.getTile(mappingCoordinates); 
		return  pGame.tileIsClicked(clicked); 	
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
	 * The Row and Column coordinates of the tile to hire a villager on
	 * @param pRow 
	 * @param pColumn 
	 * @param upgradeType
	 * 
	 */
	public static void hireUnit(Game pGame, int pRow, int pColumn, UnitType upgradeType)
	{
		Coordinates mappingCoordinates = new Coordinates(pRow, pColumn);
		Tile toHireOn = pGame.getTile(mappingCoordinates); 
		pGame.hireVillager(toHireOn, upgradeType);
	}

	/**
	 * 
	 * @param pGame
	 * @param pStartRow
	 * @param pStartColumn
	 * @param pDestRow
	 * @param pDestColumn
	 */
	public static void moveUnit(Game pGame, int pStartRow, int pStartColumn, int pDestRow, int pDestColumn )
	{
		Coordinates startTileCoord = new Coordinates(pStartRow, pStartColumn);
		Tile startTile = pGame.getTile(startTileCoord); 
		Coordinates destTileCoord = new Coordinates(pDestRow, pDestColumn);
		
		Tile destTile = pGame.getTile(destTileCoord); 
		pGame.moveUnit(startTile, destTile);
	}

	/**
	 * 
	 * @param pGame
	 * @param pRow
	 * @param pColumn
	 * @param pActionType
	 */
	public static void setActionType(Game pGame, int pRow, int pColumn, ActionType pActionType)
	{
		Coordinates pTileCoord = new Coordinates(pRow, pColumn);
		Tile pTile = pGame.getTile(pTileCoord); 
		pGame.setActionType(pTile, pActionType);
	}

	/**
	 * 
	 * @param pGame
	 * @param pRow
	 * @param pColumn
	 * @param upgradeType
	 */
	public static void upgradeUnit(Game pGame, int pRow, int pColumn, UnitType upgradeType)
	{
		Coordinates mappingCoordinates = new Coordinates(pRow, pColumn);
		Tile toUpgradeOn = pGame.getTile(mappingCoordinates); 
		pGame.upgradeUnit(toUpgradeOn, upgradeType);
	}

	/**
	 * Upgrades the village on pRow and pColumn to type pVillageType in pGame
	 * @param pGame
	 * @param pRow
	 * @param pColumn
	 * @param pVillageType
	 */
	public static void upgradeVillage(Game pGame, int pRow, int pColumn, VillageType pVillageType)
	{
		Coordinates mappingCoordinates = new Coordinates(pRow, pColumn); 
		Tile villageTile = pGame.getTile(mappingCoordinates); 
		Village pVillage = pGame.getVillage(villageTile);
		pGame.upgradeVillage(pVillage, pVillageType);

	}
}
