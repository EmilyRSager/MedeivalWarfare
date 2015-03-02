package mw.client.controller;

import java.util.Collection;

import mw.client.gui.ImageTile;
import mw.client.model.*;

public final class ActionInterpreter /*implements Controller */{

	
	/* ===============================
	 * 		Generic Controller
	 * ===============================
	 */
	
	
	private static ActionInterpreter interpreter;
	
	public static void initialize(Game g)
	{
		interpreter = new ActionInterpreter(g);
	}
	
	public static void clear()
	{
		interpreter = null;
	}
	
	public static ActionInterpreter singleton()
	{
		return interpreter;
	}
	
	
	/* ===============================
	 * 		ActionInterpreter 
	 * ===============================
	 */
	
	
	private final Game game;
	private ModelTile selectedMTile;
	private ImageTile selectedITile;
	private Collection<GameAction> possibleActions;
	
	
	/* ========================
	 * 		Constructors
	 * ========================
	 */
	
	
	private ActionInterpreter(Game g)
	{
		game = g;
		selectedMTile = null;
		selectedITile = null;
		possibleActions = null;
	}
	
	
	/* ========================
	 * 		Public methods
	 * ========================
	 */
	
	
	public void primarySelect(ImageTile dispTarget)
	{
		// get the Tile
		ModelTile modelTarget = ModelViewMapping.singleton().getModelTile(dispTarget);
		if (isSelectable(modelTarget)) {
			selectedMTile = modelTarget;
			selectedITile = dispTarget;
			DisplayUpdater.setSelected(dispTarget, true);
			possibleActions = NetworkQueries.getPossibleMoves(selectedITile);
		} else {
			unselect();
		}
		// ask the Server for possible moves
		// it will be stored later by some writer thread
	}
	
	
	public void secondarySelect(ImageTile dispTarget)
	{
		// get the Tile
		ModelTile target;
		
	}
	
	
	/*public void setPossibleActions(ModelTile concernedTile, Collection<GameAction> actions)
	{
		// Not a good solution !
		if (selectedTile == concernedTile) {	// comparing the references on purpose
			possibleActions = actions;
		} else {
			// do nothing, the actions are just not the one we want
		}
	}*/

	
	/* ============================
	 * 		Private methods
	 * ============================
	 */
	
	
	private boolean isSelectable(ModelTile target)
	{
		boolean correctTileComponent = ModelQuerier.hasUnit(target) || ModelQuerier.hasVillage(target);
		return correctTileComponent && ModelQuerier.ownedByCurrentPlayer(game, target);
	}
	
	
	private void unselect() 
	{
		if (selectedITile!=null) {
			DisplayUpdater.setSelected(selectedITile, false);
			selectedMTile = null;
			selectedITile = null;
		}
	}
	
}
