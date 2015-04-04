/**
 * @author Charlie Bloomfield
 * Mar 8, 2015
 */

package mw.shared.servercommands;

import mw.server.gamelogic.controllers.GameController;
import mw.server.network.mappers.GameMapper;
import mw.server.network.translators.NetworkToModelTranslator;
import mw.shared.Coordinates;
import mw.shared.SharedTile;

/**
 * 
 */
public class UpgradeUnitCommand extends AbstractServerCommand {
	private final String aType = "UpgradeUnitCommand";
	private Coordinates aUnitCoordinates;
	private SharedTile.UnitType aUnitType;
	
	/**
	 * Construcutor
	 * @param pUnitCoordinated the coordinates of the tile on which the upgraded unit exists
	 * @param pUnitType the new type of the upgraded unit
	 */
	public UpgradeUnitCommand(Coordinates pUnitCoordinates, SharedTile.UnitType pUnitType) {
		aUnitCoordinates = pUnitCoordinates;
		aUnitType = pUnitType;
	}

	/**
	 * @see mw.shared.servercommands.AbstractServerCommand#execute(java.lang.Integer)
	 * @param pClientID
	 */
	@Override
	public void execute(Integer pClientID) throws Exception {
		GameController.upgradeUnit(
				GameMapper.getInstance().getGame(pClientID),
				aUnitCoordinates,
				NetworkToModelTranslator.translateUnitType(aUnitType)
				);

	}

}
