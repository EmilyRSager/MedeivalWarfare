package mw.client.controller;

import java.util.Collection;

import mw.client.controller.ChoiceCenter.ChoiceType;
import mw.client.gui.ImageTile;
import mw.client.model.*;
import mw.shared.SharedPossibleGameActions;
import mw.shared.SharedActionType;
import mw.shared.SharedTile;
import mw.shared.SharedTile.UnitType;
import mw.shared.SharedTile.VillageType;

public final class ActionInterpreter {

	
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
	private SharedPossibleGameActions possibleActions;
	//private Map<String, Map<String, GameAction>> currentChoices;
	
	
	
	private final UserActionSender actionSender;
	private final ChoiceCenter choiceCenter;
	
	
	/* ========================
	 * 		Constructors
	 * ========================
	 */
	
	
	private ActionInterpreter(Game g)
	{
		game = g;
		selectedMTile = null;
		selectedITile = null;
		
		UserActionSender.initialize();
		actionSender = UserActionSender.singleton();
		
		choiceCenter = new ChoiceCenter();
		
		clearSelect();
	}
	
	
	/* ========================
	 * 		Public methods
	 * ========================
	 */
	
	
	public void primarySelect(ImageTile dispTarget)
	{
		System.out.println("Starting primary select");
		unselect();
		
		ModelTile modelTarget = ModelViewMapping.singleton().getModelTile(dispTarget);
		if (isSelectable(modelTarget))
		{
			System.out.println("Selecting the tile");
			selectedMTile = modelTarget;
			selectedITile = dispTarget;
			
			DisplayUpdater.setSelected(dispTarget, true);
			if (ModelQuerier.hasVillage(selectedMTile))
			{
				int gold = ModelQuerier.getVillageGold(selectedMTile);
				int wood = ModelQuerier.getVillageWood(selectedMTile);
				DisplayUpdater.showVillageResources(gold, wood);
			}
			
			System.out.println("Before asking for the moves");
			actionSender.askForPossibleMoves(selectedMTile);
			System.out.println("Successfuly asked for the moves");
		}
	}
	
	
	public void secondarySelect(ImageTile dispTarget)
	{
		if (dispTarget==null)
			unselect();
		else 
		{
			possibleActions = actionSender.getPossibleActions();

			if (possibleActions!=null)
			{
				ModelTile modelTarget = ModelViewMapping.singleton().getModelTile(dispTarget);

				boolean res = actionSender.tryMoveUnit(selectedMTile, modelTarget);
				if (res)
					unselect();
			}
		}
	}
	
	public void handleEndTurn()
	{
		if (ModelQuerier.isCurrentlyPlaying(game))
		{
			actionSender.sendEndTurn();
			unselect();
		}
	}
	
	public void handleNewPossibleActions(SharedPossibleGameActions newActions)
	{
		possibleActions = newActions;
		displayPossibleActions();
	}
	
	public void notifyChoiceResult(ChoiceType choiceType, String choseItem)
	{
		choiceCenter.handleChoiceResult(choiceType, choseItem, this);
		choiceCenter.clear();
		actionSender.askForPossibleMoves(selectedMTile);
	}
	
	public void notifyVillageUpgradeChoiceResult(VillageType vt)
	{
		actionSender.sendUpgradeVillage(selectedMTile, vt);
	}

	public void notifyUnitHireChoiceResult(UnitType ut)
	{
		actionSender.sendUnitHire(selectedMTile, ut);
	}
	
	public void notifyUnitActionChoiceResult(SharedActionType at)
	{
		actionSender.sendUnitAction(selectedMTile, at);
	}
	
	/* ============================
	 * 		Private methods
	 * ============================
	 */
	
	
	private boolean isSelectable(ModelTile target)
	{
		if (target==null)
			return false;
		boolean correctTileComponent = ModelQuerier.hasUnit(target) || ModelQuerier.hasVillage(target);
		return correctTileComponent 
				&& ModelQuerier.isCurrentlyPlaying(game)
				&& ModelQuerier.ownedByCurrentPlayer(game, target);
	}
	
	private void clearSelect() 
	{
		choiceCenter.clear();
		
		actionSender.clearPossibleActions();
	}
	
	private void unselect() 
	{
		
		if (selectedITile!=null) {
			DisplayUpdater.setSelected(selectedITile, false);
			selectedMTile = null;
			selectedITile = null;
		}
		clearSelect();
	}
	
	private void displayPossibleActions()
	{
		SharedTile.VillageType vt = possibleActions.getVillageUpgrade();
		if (vt != null && vt != VillageType.NONE) {
			choiceCenter.displayVillageChoice(vt);
		}
		
		Collection<UnitType> uts = possibleActions.getUnitUpgrade();
		if (uts != null && !uts.isEmpty()) {
			choiceCenter.displayUnitTypeChoice(uts);
		}
		
		Collection<SharedActionType> ats = possibleActions.getUnitActions();
		if (ats != null && !ats.isEmpty()) {
			choiceCenter.displayUnitActionChoice(ats);
		}
	}
	

	
	
	
}
