package mw.client.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import mw.client.gui.ImageTile;
import mw.client.model.*;
import mw.client.network.NetworkController;
import mw.shared.SharedPossibleGameActions;
import mw.shared.SharedActionType;
import mw.shared.SharedCoordinates;
import mw.shared.SharedTile;
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
	
	private static final String vupChoiceName = "Select the type of village to upgrade to:";
	private Choice<SharedTile.VillageType> villageUpgradeChoice;

	private static final String uactChoiceName = "Select an action:";
	private Choice<SharedActionType> unitActionChoice;

	private static final String uhireupChoiceName = "Select a type of unit to hire:";
	private Choice<SharedTile.UnitType> unitUpgradeHireChoice;
	
	private final UserActionSender actionSender;
	
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
	
	public void handleNewPossibleActions(SharedPossibleGameActions newActions)
	{
		possibleActions = newActions;
		displayPossibleActions();
	}
	
	public void notifyChoiceResult(String choiceTitle, String choseItem)
	{
		if (choiceTitle.equals(vupChoiceName))
		{
			SharedCoordinates src = getCurrentCoordinates();
			VillageType vt = getChoiceResult(villageUpgradeChoice, choseItem);
			NetworkController.upgradeVillage(src,vt);
		}
		else if (choiceTitle.equals(uhireupChoiceName))
		{
			SharedCoordinates src = getCurrentCoordinates();
			SharedTile.UnitType ut = getChoiceResult(unitUpgradeHireChoice, choseItem);
			NetworkController.hireUnit(src,ut);
		}
		else if (choiceTitle.equals(uactChoiceName))
		{
			SharedCoordinates src = getCurrentCoordinates();
			SharedActionType at = getChoiceResult(unitActionChoice, choseItem);
			NetworkController.setUnitAction(src,at);
		}
		else {
			throw new IllegalArgumentException("The pair (choiceTitle=\""+choiceTitle+"\", choseItem=\""+choseItem+") is invalid");
		}
		
		actionSender.askForPossibleMoves();
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
		return correctTileComponent && ModelQuerier.ownedByCurrentPlayer(game, target);
	}
	
	private void clearSelect() 
	{
		villageUpgradeChoice = null;
		unitActionChoice = null;
		unitUpgradeHireChoice = null;
		
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
		displayVillageChoice(possibleActions.getVillageUpgrade());
		displayUnitTypeChoice(possibleActions.getUnitUpgrade());
		displayUnitActionChoice(possibleActions.getUnitActions());
	}
	

	private void displayVillageChoice(VillageType vt)
	{
		if (vt != null && vt != VillageType.NONE)
		{
			List<VillageType> vtList = new ArrayList<VillageType>();
			vtList.add(vt);
			villageUpgradeChoice = new Choice<SharedTile.VillageType>(vtList);
			DisplayUpdater.displayChoice(vupChoiceName, villageUpgradeChoice.itemsToString());
		}
	}
	
	private void displayUnitTypeChoice(Collection<SharedTile.UnitType> ut)
	{
		if (ut != null)
		{
			unitUpgradeHireChoice = new Choice<SharedTile.UnitType>(ut);
			DisplayUpdater.displayChoice(uhireupChoiceName, unitUpgradeHireChoice.itemsToString());
		}
	}

	private void displayUnitActionChoice(Collection<SharedActionType> unitActions)
	{
		if (unitActions!=null)
		{
			unitActionChoice = new Choice<SharedActionType>(unitActions);
			DisplayUpdater.displayChoice(uactChoiceName, unitActionChoice.itemsToString());
		}
	}
	
	private <T> T getChoiceResult(Choice<T> choice, String strRes)
	{
		if (choice == null)
			throw new IllegalArgumentException("No choice available, impossible to select anything");
		return choice.getItem(strRes);
	}
	
	
}
