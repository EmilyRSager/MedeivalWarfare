/**
 * @author Charlie Bloomfield
 * March 4, 2015
 */

package mw.server.network.controllers;
import mw.server.gamelogic.Game;
import mw.server.gamelogic.GameController;
import mw.server.gamelogic.PossibleGameActions;
import mw.server.network.mappers.ClientChannelMapper;
import mw.server.network.translators.SharedPossibleActionsTranslator;
import mw.shared.SharedPossibleGameActions;
import mw.shared.SharedCoordinates;
import mw.shared.clientcommands.DisplayPossibleGameActionsCommand;

public class GetPossibleActionsController {
	public static void getPossibleActions(Integer pClientID, Game pGame, SharedCoordinates pSharedCoordinates){
		PossibleGameActions lPossibleGameActions = 
				GameController.getPossibleGameActions(
						pGame, 
						pSharedCoordinates.getX(), 
						pSharedCoordinates.getY()
					);
		
		//translate to shared action representation
		SharedPossibleGameActions lSharedPossibleGameActions = 
				SharedPossibleActionsTranslator.translatePossibleGameActions(lPossibleGameActions);
		
		ClientChannelMapper
			.getInstance()
			.getChannel(pClientID)
			.sendCommand(new DisplayPossibleGameActionsCommand(lSharedPossibleGameActions));
	}
}
