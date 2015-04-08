/**
 * @author Charlie Bloomfield
 * Mar 8, 2015
 */

package mw.shared.servercommands;

import java.util.UUID;

import mw.server.gamelogic.controllers.GameController;
import mw.server.gamelogic.exceptions.CantUpgradeException;
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
public class UpgradeVillageCommand extends AbstractAuthenticatedServerCommand {
	private final String aType = "UpgradeVillageCommand";
	private Coordinates aVillageCoordinates;
	private SharedTile.VillageType aVillageType;
	
	/**
	 * Constructor
	 */
	public UpgradeVillageCommand(Coordinates pVillageCoordinates, SharedTile.VillageType pVillageType) {
		aVillageCoordinates = pVillageCoordinates;
		aVillageType = pVillageType;
	}

	@Override
	protected void doExecution(UUID pAccountID) throws CantUpgradeException, NotEnoughIncomeException, IllegalCommandException {
		GameController.upgradeVillage(
				GameMapper.getInstance().getGame(pAccountID),
				aVillageCoordinates,
				NetworkToModelTranslator.translateVillageType(aVillageType)
				);
	}


}
