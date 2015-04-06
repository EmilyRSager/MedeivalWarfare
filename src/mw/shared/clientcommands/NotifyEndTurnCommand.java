package mw.shared.clientcommands;

import mw.client.controller.netmodel.GameCommandHandler;

public class NotifyEndTurnCommand extends AbstractClientCommand {
	private final String aType = "NotifyEndTurnCommand";

	@Override
	public void execute() {
		GameCommandHandler.setNowPlaying(false);
	}
}
