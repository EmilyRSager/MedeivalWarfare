package mw.shared.clientcommands;

import mw.shared.SharedCreatedGame;

public class InviteToLoadedGameCommnad extends AbstractClientCommand {
	private final String aType = "InviteToLoadedGameCommand";
	private SharedCreatedGame aSharedCreatedGame;
	
	public InviteToLoadedGameCommnad(SharedCreatedGame pSharedCreatedGame) {
		aSharedCreatedGame = pSharedCreatedGame;
	}
	
	@Override
	public void execute() {
		//TODO
	}

}
