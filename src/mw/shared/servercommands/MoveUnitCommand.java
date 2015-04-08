/**
 * @author Charlie Bloomfield
 * Mar 8, 2015
 */

package mw.shared.servercommands;

import java.util.UUID;

import mw.server.gamelogic.controllers.GameController;
import mw.server.network.exceptions.IllegalCommandException;
import mw.server.network.mappers.AccountMapper;
import mw.server.network.mappers.GameMapper;
import mw.shared.Coordinates;

/**
 * 
 */
public class MoveUnitCommand extends AbstractAuthenticatedServerCommand {
	private final String aType = "MoveUnitCommand";
	private Coordinates aSourceCoordinates;
	private Coordinates aDestinationCoordinates;

	/**
	 * Constructor
	 * @param pSourceCoordinates
	 * @param pDestinationCoordinates
	 */
	public MoveUnitCommand(Coordinates pSourceCoordinates, Coordinates pDestinationCoordinates){
		aSourceCoordinates = pSourceCoordinates;
		aDestinationCoordinates = pDestinationCoordinates;
	}

	@Override
	protected void doExecution(UUID pAccountID) throws IllegalCommandException {
		GameController.moveUnit(
				GameMapper.getInstance().getGame(pAccountID),
				aSourceCoordinates,
				aDestinationCoordinates
				);
	}
}
