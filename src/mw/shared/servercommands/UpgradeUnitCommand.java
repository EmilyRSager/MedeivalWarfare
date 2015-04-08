/**
 * @author Charlie Bloomfield
 * Mar 8, 2015
 */

package mw.shared.servercommands;

import java.util.UUID;

import mw.server.gamelogic.controllers.GameController;
import mw.server.gamelogic.exceptions.NotEnoughIncomeException;
import mw.server.network.exceptions.IllegalCommandException;
import mw.server.network.mappers.AccountMapper;
import mw.server.network.mappers.GameMapper;
import mw.server.network.translators.NetworkToModelTranslator;
import mw.shared.Coordinates;
import mw.shared.SharedTile;

/**
 * 
 */
public class UpgradeUnitCommand extends AbstractAuthenticatedServerCommand {
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

	@Override
	protected void doExecution(UUID pAccountID) throws NotEnoughIncomeException {
		GameController.upgradeUnit(
				GameMapper.getInstance().getGame(pAccountID),
				aUnitCoordinates,
				NetworkToModelTranslator.translateUnitType(aUnitType)
				);
	}

}
