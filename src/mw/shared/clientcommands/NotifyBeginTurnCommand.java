package mw.shared.clientcommands;

import mw.client.controller.netmodel.GameCommandHandler;

public class NotifyBeginTurnCommand extends AbstractClientCommand{
	private final String aType = "NotifyBeginTurnCommand";
	

	@Override
	public void execute() {
		GameCommandHandler.setNowPlaying(true);
	}
}
