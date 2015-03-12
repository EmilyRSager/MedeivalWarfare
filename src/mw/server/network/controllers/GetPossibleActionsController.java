/**
 * @author Charlie Bloomfield
 * March 4, 2015
 */

package mw.server.network.controllers;

import mw.server.gamelogic.controllers.GameController;
import mw.server.gamelogic.state.Game;
import mw.server.network.mappers.ClientChannelMapper;
import mw.server.network.translators.SharedPossibleActionsTranslator;
import mw.shared.Coordinates;
import mw.shared.SharedPossibleGameActions;
import mw.shared.clientcommands.DisplayPossibleGameActionsCommand;

public class GetPossibleActionsController {
	public static void getPossibleActions(Integer pClientID, Game pGame, Coordinates pCoordinates){
		PossibleGameActions lPossibleGameActions = 
				GameController.getPossibleGameActions(pGame, pCoordinates);

		//translate to shared action representation
		SharedPossibleGameActions lSharedPossibleGameActions = 
				SharedPossibleActionsTranslator.translatePossibleGameActions(lPossibleGameActions);

		ClientChannelMapper
				.getInstance()
				.getChannel(pClientID)
				.sendCommand(new DisplayPossibleGameActionsCommand(lSharedPossibleGameActions));
	}
}
