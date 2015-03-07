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
		Game crtGame = new Game(gamePlayers, 0);
		return crtGame;
	}
	
	public static PossibleGameActions getPossibleGameActions(Game pGame, int pRow, int pColumn)
	{
		
		Coordinates mappingCoordinates = new Coordinates(pRow, pColumn);
		Tile clicked = pGame.getTile(mappingCoordinates); 
		return  pGame.tileIsClicked(clicked); 
		
		
	}
	
	public static void updateGameState(MoveType pMoveType, Game pGame, int pRow1, int pColumn1, int pRow2, int pColumn2)
	{
		//TODO
	}
}
