/**
 * @author Charlie Bloomfield
 * Mar 8, 2015
 */

package mw.shared.servercommands;

import java.util.UUID;

import mw.server.gamelogic.controllers.GameController;
import mw.server.network.mappers.AccountMapper;
import mw.server.network.mappers.GameMapper;
import mw.shared.Coordinates;

/**
 * 
 */
public class MoveUnitCommand extends AbstractServerCommand {
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

	/**
	 * @see mw.shared.servercommands.AbstractServerCommand#execute(java.lang.Integer)
	 */
	@Override
	public void execute(Integer pClientID) throws Exception {
		UUID lAccountID = AccountMapper.getInstance().getAccountID(pClientID);
		GameController.moveUnit(
				GameMapper.getInstance().getGame(lAccountID),
				aSourceCoordinates,
				aDestinationCoordinates
				);

	}
}
