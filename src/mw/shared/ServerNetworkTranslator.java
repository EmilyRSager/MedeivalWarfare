package mw.shared;

import java.util.ArrayList;
import java.util.Collection;

import mw.server.gamelogic.ActionType;
import mw.server.gamelogic.PossibleActions;
import mw.server.gamelogic.Tile;
import mw.server.gamelogic.TileController;
import mw.server.gamelogic.UnitType;

public abstract class ServerNetworkTranslator {



	/* ========================
	 * 		Static methods
	 * ========================
	 */
	
	public static PossibleGameActions translatePossibleGameActions(PossibleActions serverPossibleActions)
	{
		Collection<SharedCoordinates> sharedMoves = new ArrayList<SharedCoordinates>();
		Collection<Tile> serverMoves = serverPossibleActions.getMovableTiles();
		for (Tile t : serverMoves) {
			sharedMoves.add(SharedTileTranslator.translateCoordinates(TileController.getCoordinates(t)));
		}
		
		Collection<SharedTile.UnitType> sharedUT = new ArrayList<SharedTile.UnitType>();
		Collection<UnitType> serverUT = serverPossibleActions.getUnitUpgrade();
		for (UnitType ut : serverUT) {
			sharedUT.add(SharedTileTranslator.translateUnitType(ut));
		}
		
		Collection<SharedActionType> sharedAT = new ArrayList<SharedActionType>();
		Collection<ActionType> serverAT = serverPossibleActions.getActions();
		for (ActionType at : serverAT) {
			sharedAT.add(translateActionType(at));
		}
		
		SharedTile.VillageType sharedVT = SharedTileTranslator.translateVillageType(serverPossibleActions.getVillageUpgrade());
		
		return new PossibleGameActions(sharedMoves, sharedUT, sharedAT, sharedVT);
	}
	
	public static SharedActionType translateActionType(ActionType at) {
		switch(at)
		{
		case BUILDINGROAD:
			return SharedActionType.BUILD_ROAD;
			
		case CULTIVATING_BEGIN:
			return SharedActionType.CULTIVATE_MEADOW;
			
			default:
				throw new IllegalArgumentException(at+" is not a legal value to translate to a SharedActionType");
		}
	}

}