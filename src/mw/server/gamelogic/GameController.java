/**
 * @author Charlie Bloomfield, Emily Sager
 * March 4, 2015
 */

package mw.server.gamelogic;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Controls all game action requests.
 */
public class GameController {

	/**
	 * 
	 * @param pGame
	 * @param pRow
	 * @param pColumn
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

	public static void moveUnit(Game pGame, int pStartRow, int pStartColumn, int pDestRow, int pDestColumn )
	{
		Coordinates startTileCoord = new Coordinates(pStartRow, pStartColumn);
		Tile startTile = pGame.getTile(startTileCoord); 
		Coordinates destTileCoord = new Coordinates(pDestColumn, pDestRow);
		Tile destTile = pGame.getTile(destTileCoord); 

		pGame.moveUnit(startTile, destTile);
	}

	public static void setActionType(Game pGame, int pRow, int pColumn, ActionType pActionType)
	{
		Coordinates pTileCoord = new Coordinates(pRow, pColumn);
		Tile pTile = pGame.getTile(pTileCoord); 
		pGame.setActionType(pTile, pActionType);
	}

	public static void upgradeUnit(Game pGame, int pRow, int pColumn, UnitType upgradeType)
	{
		Coordinates mappingCoordinates = new Coordinates(pRow, pColumn);
		Tile toUpgradeOn = pGame.getTile(mappingCoordinates); 
		pGame.upgradeUnit(toUpgradeOn, upgradeType);
	}

	public static void upgradeVillage(Game pGame, int pRow, int pColumn, VillageType pVillageType)
	{
		Coordinates mappingCoordinates = new Coordinates(pRow, pColumn); 
		Tile villageTile = pGame.getTile(mappingCoordinates); 
		Village pVillage = pGame.getVillage(villageTile);
		pGame.upgradeVillage(pVillage, pVillageType);

	}


}
