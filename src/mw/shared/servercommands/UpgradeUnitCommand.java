/**
 * @author Charlie Bloomfield
 * Mar 8, 2015
 */

package mw.shared.servercommands;

import mw.server.gamelogic.GameController;
import mw.server.network.mappers.GameMapper;
import mw.server.network.translators.NetworkToModelTranslator;
import mw.shared.SharedCoordinates;
import mw.shared.SharedTile;

/**
 * 
 */
public class UpgradeUnitCommand extends AbstractServerCommand {
	private final String aType = "UpgradeUnitCommand";
	private SharedCoordinates aUnitCoordinates;
	private SharedTile.UnitType aUnitType;
	
	/**
	 * Construcutor
	 * @param pUnitCoordinated the coordinates of the tile on which the upgraded unit exists
	 * @param pUnitType the new type of the upgraded unit
	 */
	public UpgradeUnitCommand(SharedCoordinates pUnitCoordinates, SharedTile.UnitType pUnitType) {
		aUnitCoordinates = pUnitCoordinates;
		aUnitType = pUnitType;
	}
	
	/**
	 * @see mw.shared.servercommands.AbstractServerCommand#isValid(java.lang.Integer)
	 * @param pClientID
	 */
	@Override
	public boolean isValid(Integer pClientID) {
		return true;
	}

	/**
	 * @see mw.shared.servercommands.AbstractServerCommand#execute(java.lang.Integer)
	 * @param pClientID
	 */
	@Override
	public void execute(Integer pClientID) {
		GameController.upgradeUnit(
				GameMapper.getInstance().getGame(pClientID),
				aUnitCoordinates.getX(),
				aUnitCoordinates.getY(),
				NetworkToModelTranslator.translateUnitType(aUnitType)
				);

	}

}
