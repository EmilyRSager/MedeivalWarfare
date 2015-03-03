package mw.client.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
	private PossibleActions/*Collection<GameAction>*/ possibleActions;
	private Map<String, Map<String, GameAction>> currentChoices;
	
	
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
		currentChoices = new HashMap<String, Map<String, GameAction>>();
	}
	
	
	/* ========================
	 * 		Public methods
	 * ========================
	 */
	
	
	public void primarySelect(ImageTile dispTarget)
	{
		unselect();
		
		ModelTile modelTarget = ModelViewMapping.singleton().getModelTile(dispTarget);
		if (isSelectable(modelTarget)) {
			selectedMTile = modelTarget;
			selectedITile = dispTarget;
			
			DisplayUpdater.setSelected(dispTarget, true);
			if (ModelQuerier.hasVillage(selectedMTile))
			{
				int gold = ModelQuerier.getVillageGold(selectedMTile);
				int wood = ModelQuerier.getVillageWood(selectedMTile);
				DisplayUpdater.showVillageResources(gold, wood);
			}
			
			possibleActions = NetworkQueries.getPossibleMoves(selectedITile);
			displayPossibleActions();
		}
	}
	
	
	public void secondarySelect(ImageTile dispTarget)
	{
		if (possibleActions!=null)
		{
			ModelTile modelTarget = ModelViewMapping.singleton().getModelTile(dispTarget);
			GameAction desiredAction = new MoveAction(selectedMTile,modelTarget);
			
			if (possibleActions.contains(desiredAction)) {
				Networking.sendCommand(new GameActionCommand(desiredAction));
				unselect();
			}
		}
	}
	
	public void notifyChoiceResult(String choiceTitle, String choseItem)
	{
		
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
		if (target==null)
			return false;
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
		possibleActions = null;
		currentChoices.clear();
	}
	
	private void displayPossibleActions()
	{
		Set<VillageUpgradeAction> vUpgrade = possibleActions.getVillageUpgradeActions();
		if (vUpgrade!=null && !vUpgrade.isEmpty())
		{
			List<String> choiceNames = new ArrayList<String>();
			HashMap<String, GameAction> vuChoices = new HashMap<String, GameAction>();
			for (VillageUpgradeAction vup : vUpgrade)
			{
				String choiceName = vup.getStructureType().stringValue();
				choiceNames.add(choiceName);
				vuChoices.put(choiceName, vup);
			}
			DisplayUpdater.displayChoice("Upgrade Village :", choiceNames);
			currentChoices.put("Upgrade Village :", vuChoices);
		}
	}
	
}
