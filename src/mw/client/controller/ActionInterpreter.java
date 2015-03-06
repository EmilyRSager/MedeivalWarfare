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
import mw.shared.PossibleGameActions;
import mw.shared.SharedActionType;
import mw.shared.SharedCoordinates;
import mw.shared.SharedTile;
import mw.shared.SharedTile.VillageType;

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
	
	private class Choice<ItemType> {
		private Collection<ItemType> items;
		
		public Choice(Collection<ItemType> choiceItems) {
			items = choiceItems;
		}
		
		public ItemType getItem(String name) {
			for (ItemType item : items) {
				if (item.toString().equals(name))
					return item;
			}
			throw new IllegalArgumentException("No item with the name "+name+" in this choice");
		}
		
		public List<String> itemsToString() {
			List<String> ret = new ArrayList<String>();
			for (ItemType item : items)
				ret.add(item.toString());
			return ret;
		}
	}
	
	
	private final Game game;
	private ModelTile selectedMTile;
	private ImageTile selectedITile;
	private PossibleGameActions possibleActions;
	//private Map<String, Map<String, GameAction>> currentChoices;
	
	private static final String vupChoiceName = "Select the type of village to upgrade to:";
	private Choice<SharedTile.VillageType> villageUpgradeChoice;

	private static final String uactChoiceName = "Select an action:";
	private Choice<SharedActionType> unitActionChoice;

	private static final String uhireupChoiceName = "Select a type of unit to hire:";
	private Choice<SharedTile.UnitType> unitUpgradeHireChoice;
	
	
	private final ReentrantLock possibleActionsLock = new ReentrantLock();
	private final Condition possibleActionsReady = possibleActionsLock.newCondition();
	//private boolean waitingForActions;
	private boolean actionsReady;
	
	/* ========================
	 * 		Constructors
	 * ========================
	 */
	
	
	private ActionInterpreter(Game g)
	{
		game = g;
		selectedMTile = null;
		selectedITile = null;
		clearSelect();
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
			
			askForPossibleMoves();
		}
	}
	
	
	public void secondarySelect(ImageTile dispTarget)
	{
		if (dispTarget==null)
			unselect();
		else {
			waitForActions();
			if (possibleActions!=null)
			{
				ModelTile modelTarget = ModelViewMapping.singleton().getModelTile(dispTarget);
				SharedCoordinates coordTarget = Translator.translateToSharedCoordinates(modelTarget.getCoordinates());
				//GameAction desiredAction = new MoveAction(selectedMTile,modelTarget);
				
				if (possibleActions.getMovableTiles().contains(coordTarget)) {
					Networking.sendCommand(new MoveAction(coordTarget));
					unselect();
				}
			}
		}
	}
	
	public void notifyChoiceResult(String choiceTitle, String choseItem)
	{
		if (choiceTitle.equals(vupChoiceName)) {
			VillageType vt = getChoiceResult(villageUpgradeChoice, choseItem);
			Networking.sendCommand();
		}
		else if (choiceTitle.equals(uhireupChoiceName)) {
			SharedTile.UnitType ut = getChoiceResult(unitUpgradeHireChoice, choseItem);
			Networking.sendCommand();
		}
		else if (choiceTitle.equals(uactChoiceName)) {
			SharedActionType at = getChoiceResult(unitActionChoice, choseItem);
			Networking.sendCommand();
		}
		else {
			throw new IllegalArgumentException("The pair (choiceTitle=\""+choiceTitle+"\", choseItem=\""+choseItem+") is invalid");
		}
		
		askForPossibleMoves();
	}
	
	public void setPossibleActions(PossibleGameActions actions)
	{
		possibleActionsLock.lock();
		
		possibleActions = actions;
		displayPossibleActions();
		actionsReady = true;
		possibleActionsReady.signalAll();
		
		possibleActionsLock.unlock();
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
	
	private void clearSelect() {
		villageUpgradeChoice = null;
		unitActionChoice = null;
		unitUpgradeHireChoice = null;
		
		possibleActionsLock.lock();
		
		possibleActions = null;
		//waitingForActions = false;
		actionsReady = false;
		
		possibleActionsLock.unlock();
	}
	
	private void unselect() 
	{
		waitForActions();
		
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
	
	private void waitForActions() {
		possibleActionsLock.lock();
		
		if (/*waitingForActions &&*/ !actionsReady) {
			try	{
				possibleActionsReady.wait();
			}
			catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		//waitingForActions = false;
		actionsReady = true;
		
		possibleActionsLock.unlock();
	}
	
	private void askForPossibleMoves()
	{
		actionsReady = false;
		NetworkQueries.getPossibleMoves(selectedITile);
	}
}
