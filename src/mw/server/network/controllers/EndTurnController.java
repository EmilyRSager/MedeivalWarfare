package mw.server.network.controllers;



import java.util.UUID;

import mw.server.gamelogic.controllers.GameController;
import mw.server.gamelogic.state.Game;
import mw.server.gamelogic.state.Player;
import mw.server.network.communication.ClientCommunicationController;
import mw.server.network.mappers.PlayerMapper;
import mw.shared.clientcommands.NotifyBeginTurnCommand;
import mw.shared.clientcommands.NotifyEndTurnCommand;

public class EndTurnController {
	
	/**
	 * Calls the GameController endTurn method. Right now, there is no way to access client information from game state,
	 * the connections are all in the opposite direction I.E. given a player, there's no simple way to find which client
	 * is associated with that Player.
	 * 
	 * As a temporary solution, I am maintaining a mapping from Player to Client in the PlayerMapper class. This seems
	 * like a mediocre solution, but so does every other solution to this problem. We'll probably need Game/Player
	 * to be an Observable later on, as adding two way mapping every time we need to map from game entities back to
	 * clients is not smart (I don't think, maybe it's ok).
	 * @param pGame
	 */
	public static void endTurn(Game pGame, UUID pEndingAccountID){
		ClientCommunicationController.sendCommand(pEndingAccountID, new NotifyEndTurnCommand());
		GameController.endTurn(pGame);
		
		Player lNewCurrentPlayer = GameController.getCurrentPlayer(pGame);
		UUID lAccountID = PlayerMapper.getInstance().getAccount(lNewCurrentPlayer);
		ClientCommunicationController.sendCommand(lAccountID, new NotifyBeginTurnCommand());
	}
}
