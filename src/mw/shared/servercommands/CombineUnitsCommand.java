package mw.shared.servercommands;

import java.util.UUID;

import mw.server.gamelogic.controllers.GameController;
import mw.server.network.mappers.AccountMapper;
import mw.server.network.mappers.GameMapper;
import mw.shared.Coordinates;

public class CombineUnitsCommand extends AbstractServerCommand{
	private final String aType = "CombineUnitsCommand";
	private Coordinates aSourceCoordinates, aTargetCoordinates;
	
	public CombineUnitsCommand(Coordinates pSourceCoordinates, Coordinates pTargetCoordinates) {
		aSourceCoordinates = pSourceCoordinates;
		aTargetCoordinates = pTargetCoordinates;
	}
	
	@Override
	public void execute(Integer pClientID) throws Exception {
		UUID lAccountID = AccountMapper.getInstance().getAccountID(pClientID);
		GameController.combineVillagers(GameMapper.getInstance().getGame(lAccountID), aSourceCoordinates, aTargetCoordinates);
	}
}
