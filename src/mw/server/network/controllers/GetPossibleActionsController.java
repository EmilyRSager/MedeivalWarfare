/**
 * @author Charlie Bloomfield
 * March 4, 2015
 */

package mw.server.network.controllers;

import java.util.UUID;

import mw.server.gamelogic.PossibleGameActions;
import mw.server.gamelogic.controllers.GameController;
import mw.server.gamelogic.state.Game;
import mw.server.network.communication.ClientCommunicationController;
import mw.server.network.mappers.ClientChannelMapper;
import mw.server.network.translators.SharedPossibleActionsTranslator;
import mw.shared.Coordinates;
import mw.shared.SharedPossibleGameActions;
import mw.shared.clientcommands.DisplayPossibleGameActionsCommand;

public class GetPossibleActionsController {
	public static void getPossibleActions(UUID pAccountID, Game pGame, Coordinates pCoordinates){
		PossibleGameActions lPossibleGameActions = 
				GameController.getPossibleGameActions(pGame, pCoordinates);

		//translate to shared action representation
		SharedPossibleGameActions lSharedPossibleGameActions = 
				SharedPossibleActionsTranslator.translatePossibleGameActions(lPossibleGameActions);

		ClientCommunicationController.sendCommand(pAccountID, new DisplayPossibleGameActionsCommand(lSharedPossibleGameActions));
	}
}
