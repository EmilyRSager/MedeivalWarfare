/**
 * @author Charlie Bloomfield
 * Mar 8, 2015
 */

package mw.shared.servercommands;

import mw.server.gamelogic.GameController;
import mw.server.network.mappers.GameMapper;
import mw.shared.SharedCoordinates;

/**
 * 
 */
public class MoveUnitCommand extends AbstractServerCommand {
	private final String aType = "MoveUnitCommand";
	private SharedCoordinates aSourceCoordinates;
	private SharedCoordinates aDestinationCoordinates;
	
	/**
	 * Constructor
	 * @param pSourceCoordinates
	 * @param pDestinationCoordinates
	 */
	public MoveUnitCommand(SharedCoordinates pSourceCoordinates, SharedCoordinates pDestinationCoordinates){
		aSourceCoordinates = pSourceCoordinates;
		aDestinationCoordinates = pDestinationCoordinates;
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
		GameController.moveUnit(
				GameMapper.getInstance().getGame(pClientID),
				aSourceCoordinates.getX(),
				aSourceCoordinates.getY(),
				aDestinationCoordinates.getX(),
				aDestinationCoordinates.getY()
				);

	}

}
