/**
 * @author Charlie Bloomfield
 * Mar 8, 2015
 */

package mw.shared.servercommands;

import mw.server.gamelogic.controllers.GameController;
import mw.server.gamelogic.exceptions.NotEnoughIncomeException;
import mw.server.network.mappers.GameMapper;
import mw.server.network.translators.NetworkToModelTranslator;
import mw.shared.Coordinates;
import mw.shared.SharedTile;

/**
 * 
 */
public class HireUnitCommand extends AbstractServerCommand {
	private final String aType = "HireUnitCommand";

	Coordinates aUnitCoordinates;
	SharedTile.UnitType aUnitType;

	/**
	 * 
	 */
	public HireUnitCommand(Coordinates pUnitCoordinates, SharedTile.UnitType pUnitType) {
		aUnitCoordinates = pUnitCoordinates;
		aUnitType = pUnitType;
	}

	/**
	 * @see mw.shared.servercommands.AbstractServerCommand#execute(java.lang.Integer)
	 */
	@Override
	public void execute(Integer pClientID) throws Exception {
		try {
			GameController.hireUnit(
					GameMapper.getInstance().getGame(pClientID),
					aUnitCoordinates,
					NetworkToModelTranslator.translateUnitType(aUnitType)
					);
		} catch (NotEnoughIncomeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
