/**
 * @author Charlie Bloomfield
 * Mar 8, 2015
 */

package mw.shared.servercommands;

import mw.server.gamelogic.controllers.GameController;
import mw.server.network.mappers.GameMapper;
import mw.server.network.translators.NetworkToModelTranslator;
import mw.shared.SharedActionType;
import mw.shared.SharedCoordinates;

/**
 * 
 */
public class SetActionTypeCommand extends AbstractServerCommand {
	private final String aType = "SetActionTypeCommand";
	private SharedCoordinates aUnitCoordinates;
	private SharedActionType aActionType;
	
	/**
	 * Constructor
	 * @param pUnitCoordinates
	 * @param pActionType
	 */
	public SetActionTypeCommand(SharedCoordinates pUnitCoordinates, SharedActionType pActionType) {
		aUnitCoordinates = pUnitCoordinates;
		aActionType = pActionType;
	}

	/**
	 * @see mw.shared.servercommands.AbstractServerCommand#isValid(java.lang.Integer)
	 */
	@Override
	public boolean isValid(Integer pClientID) {
		return true;
	}

	/**
	 * @see mw.shared.servercommands.AbstractServerCommand#execute(java.lang.Integer)
	 */
	@Override
	public void execute(Integer pClientID) {
		GameController.setActionType(
				GameMapper.getInstance().getGame(pClientID),
				aUnitCoordinates.getX(),
				aUnitCoordinates.getY(),
				NetworkToModelTranslator.translateActionType(aActionType)
				);

	}

}
