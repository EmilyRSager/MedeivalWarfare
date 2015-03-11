package mw.shared.clientcommands;

import mw.client.controller.GameCommandHandler;

public class NotifyBeginTurnCommand extends AbstractClientCommand{
	private final String aType = "BeginTurnCommand";
	
	@Override
	public boolean isValid() {
		return true;
	}

	@Override
	public void execute() {
		GameCommandHandler.setNowPlaying(true);
	}
}
