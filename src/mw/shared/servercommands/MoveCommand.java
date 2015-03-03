package mw.shared.servercommands;

import mw.server.gamelogic.Game;
import mw.server.gamelogic.Action;
import mw.server.network.GameController;
import mw.server.network.GameManager;


public class MoveCommand extends AbstractServerCommand {
	private final String aType = "GameMoveCommand";
	private Action aMove;
	
	/**
	 * Constructor
	 * @param pMove
	 */
	public MoveCommand(Action pMove) {
		aMove = pMove;
	}
	
	@Override
	public boolean isValid(Integer pClientID) {
		return true;
	}

	@Override
	public void execute(Integer pClientID) {
		Game lGame = GameManager.getInstance().getGame(pClientID);
		GameController.newMove(lGame, aMove);
	}
}
