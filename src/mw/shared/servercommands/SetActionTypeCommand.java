/**
 * @author Charlie Bloomfield
 * Mar 8, 2015
 */

package mw.shared.servercommands;

import java.util.UUID;

import mw.server.gamelogic.controllers.GameController;
import mw.server.network.mappers.AccountMapper;
import mw.server.network.mappers.GameMapper;
import mw.server.network.translators.NetworkToModelTranslator;
import mw.shared.Coordinates;
import mw.shared.SharedActionType;

/**
 * 
 */
public class SetActionTypeCommand extends AbstractAuthenticatedServerCommand {
	private final String aType = "SetActionTypeCommand";
	private Coordinates aUnitCoordinates;
	private SharedActionType aActionType;
	
	/**
	 * Constructor
	 * @param pUnitCoordinates
	 * @param pActionType
	 */
	public SetActionTypeCommand(Coordinates pUnitCoordinates, SharedActionType pActionType) {
		aUnitCoordinates = pUnitCoordinates;
		aActionType = pActionType;
	}

	@Override
	protected void doExecution(UUID pAccountID) throws Exception {
		GameController.setActionType(
				GameMapper.getInstance().getGame(pAccountID),
				aUnitCoordinates,
				NetworkToModelTranslator.translateActionType(aActionType)
				);
	}

}
