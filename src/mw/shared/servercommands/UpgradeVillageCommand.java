/**
 * @author Charlie Bloomfield
 * Mar 8, 2015
 */

package mw.shared.servercommands;

import mw.server.gamelogic.GameController;
import mw.server.network.mappers.GameMapper;
import mw.shared.SharedCoordinates;
import mw.shared.SharedTile;

/**
 * 
 */
public class UpgradeVillageCommand extends AbstractServerCommand {
	private final String aType = "UpgradeVillageCommand";
	private SharedCoordinates aVillageCoordinates;
	private SharedTile.VillageType aVillageType;
	
	/**
	 * Constructor
	 */
	public UpgradeVillageCommand(SharedCoordinates pVillageCoordinates, SharedTile.VillageType pVillageType) {
		aVillageCoordinates = pVillageCoordinates;
		aVillageType = pVillageType;
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
		GameController.upgradeVillage(
				GameMapper.getInstance().getGame(pClientID),
				aVillageCoordinates.getX(),
				aVillageCoordinates.getY(),
				//TODO translate aVilageType
				);

	}

}
