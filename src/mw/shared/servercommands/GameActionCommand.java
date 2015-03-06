

package mw.shared.servercommands;

import mw.server.gamelogic.Game;
import mw.server.gamelogic.GameAction;
import mw.server.network.mappers.GameMapper;


public class GameActionCommand extends AbstractServerCommand {
	private final String aType = "GameActionCommand";
	private GameAction aGameAction;
	
	/**
	 * Constructor
	 * @param pGameAction
	 */
	public GameActionCommand(GameAction pGameAction) {
		aGameAction = pGameAction;
	}
	
	@Override
	public boolean isValid(Integer pClientID) {
		return true;
	}

	@Override
	public void execute(Integer pClientID) {
		Game lGame = GameMapper.getInstance().getGame(pClientID);
		
		//TODO call controller
	}
}
