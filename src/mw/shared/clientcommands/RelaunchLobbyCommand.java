package mw.shared.clientcommands;

import mw.client.controller.menuing.MenuActionSender;
import mw.shared.SharedGameLobby;

public class RelaunchLobbyCommand extends AbstractClientCommand {
	private final String aType = "RelaunchLobbyCommand";
	private SharedGameLobby aSharedGameLobby;
	
	public RelaunchLobbyCommand(SharedGameLobby pSharedGameLobby) {
		aSharedGameLobby = pSharedGameLobby;
	}
	
	@Override
	public void execute() {
		MenuActionSender.popUpMessage(aMessage);
		MenuActionSender.leaveGame(aSharedGameLobby);
	}
}
