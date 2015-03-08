package mw.server.network.translators;

import java.util.ArrayList;
import java.util.Collection;

import mw.server.gamelogic.ActionType;
import mw.server.gamelogic.PossibleGameActions;
import mw.server.gamelogic.Tile;
import mw.server.gamelogic.TileController;
import mw.server.gamelogic.UnitType;
import mw.shared.SharedActionType;
import mw.shared.SharedCoordinates;
import mw.shared.SharedPossibleGameActions;
import mw.shared.SharedTile;
import mw.shared.SharedTile.VillageType;

public abstract class SharedPossibleActionsTranslator {



	/* ========================
	 * 		Static methods
	 * ========================
	 */
	public static SharedPossibleGameActions translatePossibleGameActions(PossibleGameActions serverPossibleActions)
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
		
		return new SharedPossibleGameActions(sharedMoves, sharedUT, sharedAT, sharedVT);
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