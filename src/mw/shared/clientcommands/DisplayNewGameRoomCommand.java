package mw.shared.clientcommands;

import mw.client.controller.menuing.MenuActionSender;
import mw.shared.SharedCreatedGame;

public class DisplayNewGameRoomCommand extends AbstractClientCommand {
	private final String aType = "DisplayNewGameRoomCommand";
	private SharedCreatedGame aNewGame;
	
	public DisplayNewGameRoomCommand(SharedCreatedGame pNewGame) {
		aNewGame = pNewGame;
	}
	
	@Override
	public void execute() {
		MenuActionSender.setJoinedGameResult(aNewGame);
	}
}
