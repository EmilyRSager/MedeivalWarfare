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
	
	public static Game newGame(Collection<Integer> pIDs) throws TooManyPlayersException 
	{
		Collection<Player> gamePlayers = new ArrayList<Player>();
		
		
		for (Integer i: pIDs)
		{
			Player lPlayer = new Player(i); 
			gamePlayers.add(lPlayer); 
		}
		
	
		Game crtGame = new Game(gamePlayers, 0); 
		return crtGame;
	}
	public static Collection<CollectionOfPossibleActions> getPossibleGameActions(Game pGame, int pRow, int pColumn)
	{
		//TODO
		return null;
	}
	
	public static void updateGameState(MoveType pMoveType, Game pGame, int pRow1, int pColumn1, int pRow2, int pColumn2)
	{
		//TODO
	}
}
