package mw.shared.clientcommands;

import java.util.UUID;

import mw.shared.SharedGameLobby;

public class DisplayGameLobbyCommand extends AbstractClientCommand{
	private final String aType = "DisplayGameLobbyCommand";
	private SharedGameLobby aSharedGameLobby;
	
	/**
	 * Constructor
	 * @param pSharedGameLobby
	 */
	public DisplayGameLobbyCommand(SharedGameLobby pSharedGameLobby) {
		aSharedGameLobby = pSharedGameLobby;
	}

	/**
	 * 
	 */
	@Override
	public void execute() {
		//TODO
	}

}
