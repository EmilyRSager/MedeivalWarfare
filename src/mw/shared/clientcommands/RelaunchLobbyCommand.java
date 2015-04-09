package mw.shared.clientcommands;

import mw.client.controller.menuing.MenuActionSender;
import mw.client.controller.netmodel.GameCommandHandler;
import mw.shared.SharedGameLobby;

public class RelaunchLobbyCommand extends AbstractClientCommand {
	private final String aType = "RelaunchLobbyCommand";
	private SharedGameLobby aSharedGameLobby;
	private String aMessage;
	
	public RelaunchLobbyCommand(SharedGameLobby pSharedGameLobby, String pMessage) {
		aSharedGameLobby = pSharedGameLobby;
		aMessage = pMessage;
	}
	
	@Override
	public void execute() {
		MenuActionSender.popUpMessage(aMessage);
		GameCommandHandler.leaveGame(aSharedGameLobby);
	}
}
