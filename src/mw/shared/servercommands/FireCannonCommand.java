package mw.shared.servercommands;

import java.util.UUID;

import mw.server.gamelogic.controllers.GameController;
import mw.server.network.mappers.GameMapper;
import mw.shared.Coordinates;

public class FireCannonCommand extends AbstractAuthenticatedServerCommand {
	private final String aType = "FireCannonCommand";
	private Coordinates aCannonCoordinates;
	private Coordinates aTargetCoordinates;
	
	public FireCannonCommand(Coordinates pSourceCoordinates, Coordinates pTargetCoordinates) {
		aCannonCoordinates = pSourceCoordinates;
		aTargetCoordinates = pTargetCoordinates;
	}
	
	
	@Override
	protected void doExecution(UUID pAccountID) {
		GameController.fireCannon(
				GameMapper.getInstance().getGame(pAccountID),
				aCannonCoordinates, 
				aTargetCoordinates
				);
	}
}
