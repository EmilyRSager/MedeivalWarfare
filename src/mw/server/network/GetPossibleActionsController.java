/**
 * @author Charlie Bloomfield
 * March 4, 2015
 */

package mw.server.network;

import mw.server.gamelogic.Game;
import mw.server.gamelogic.GameController;
import mw.shared.SharedCoordinates;

public class GetPossibleActionsController {
	public static void getPossibleActions(Integer pClientID, Game pGame, SharedCoordinates pSharedCoordinates){
		CollectionOfPossibleActions lPossibleGameActions = 
				GameController.getPossibleGameActions(
						pGame, 
						pSharedCoordinates.getX(), 
						pSharedCoordinates.getY()
						);

		
	}
}
