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
public class UpgradeVillageCommand extends AbstractServerCommand {
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
	/**
	 * @see mw.shared.servercommands.AbstractServerCommand#execute(java.lang.Integer)
	 */
	@Override
	public void execute(Integer pClientID) throws Exception {
		GameController.upgradeVillage(
				GameMapper.getInstance().getGame(pClientID),
				aVillageCoordinates,
				NetworkToModelTranslator.translateVillageType(aVillageType)
				);

	}

}
