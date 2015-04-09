package mw.client.controller.guimodel;

import java.util.Collection;

import mw.client.app.test.GameWindow;
import mw.client.controller.guimodel.ChoiceCenter.ChoiceType;
import mw.client.controller.model.ModelQuerier;
import mw.client.controller.netmodel.UserActionSender;
import mw.client.gui.window.ImageTile;
import mw.client.model.*;
import mw.shared.SharedPossibleGameActions;
import mw.shared.SharedActionType;
import mw.shared.SharedTile;
import mw.shared.SharedTile.UnitType;
import mw.shared.SharedTile.VillageType;
import mw.shared.servercommands.FireCannonCommand;

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
	
	
	private final UserActionSender actionSender;
	private final ChoiceCenter choiceCenter;
	
	
	
	private boolean hiringUnit;
	private UnitType hiredUnitType;
	
	private boolean firingCannon;
	
	
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
		
		if (dispTarget == null) {
			unselect();
			return;
		}
		
		if (hiringUnit)
		{
			if (hiredUnitType == null)
				throw new IllegalStateException("User is currently hiring a Unit, but no hired unit type has been selected");

			ModelTile modelTarget = ModelViewMapping.singleton().getModelTile(dispTarget);
			boolean status = actionSender.tryUnitHire(modelTarget, hiredUnitType);
			
			if (!status)
				DisplayUpdater.showGeneralMessage("You can't hire on this tile");
			
			unselect();
		}
		else if (firingCannon)
		{
			ModelTile modelTarget = ModelViewMapping.singleton().getModelTile(dispTarget);
			boolean status = actionSender.tryFireCannon(selectedMTile, modelTarget);
			
			if (!status)
				DisplayUpdater.showGeneralMessage("You can't fire at this tile");
			
			unselect();
		}
		else
		{
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
				
				actionSender.askForPossibleMoves(selectedMTile);
				System.out.println("Successfuly asked for the moves");
			}
		}
	}
	
	
	public void secondarySelect(ImageTile dispTarget)
	{
		System.out.println("Starting secondary select");
		if (dispTarget==null || hiringUnit || dispTarget == selectedITile)
			unselect();
		else 
		{
			possibleActions = actionSender.getPossibleActions();
			System.out.println("Got the possible actions");

			if (possibleActions!=null)
			{
				ModelTile modelTarget = ModelViewMapping.singleton().getModelTile(dispTarget);

				boolean res = actionSender.tryMoveUnit(selectedMTile, modelTarget);
				//System.out.println("Tried sending move unit, result = "+res);
				if (res)
					unselect();
				else
					DisplayUpdater.showGeneralMessage("You can't move here");
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
		//choiceCenter.clear();
		//actionSender.askForPossibleMoves(selectedMTile);
	}
	
	public void notifyVillageUpgradeChoiceResult(VillageType vt)
	{
		actionSender.sendUpgradeVillage(selectedMTile, vt);
		unselect();
	}

	public void notifyUnitHireChoiceResult(UnitType ut)
	{
		hiringUnit = true;
		hiredUnitType = ut;
		DisplayUpdater.showGeneralMessage("Click on the tile you want to hire your "+ut+" on");
	}
	
	public void notifyUnitActionChoiceResult(SharedActionType at)
	{
		actionSender.sendUnitAction(selectedMTile, at);
		unselect();
	}

	public void notifyUnitUpgradeChoiceResult(UnitType ut)
	{
		actionSender.sendUnitUpgrade(selectedMTile, ut);
		unselect();
	}
	

	public void startFiringCannon()
	{
		firingCannon = true;
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
		hiringUnit = false;
		hiredUnitType = null;
		firingCannon = false;
		
		choiceCenter.clear();
		
		actionSender.clearPossibleActions();
		DisplayUpdater.showGeneralMessage("");
		DisplayUpdater.showFireButton(false);
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
			choiceCenter.displayVillageUpgradeChoice(vt);
		}
		
		Collection<UnitType> uts = possibleActions.getUnitUpgrade();
		if (uts != null && !uts.isEmpty()) {
			if (ModelQuerier.hasVillage(selectedMTile))
				choiceCenter.displayUnitHireChoice(uts);
			else if (ModelQuerier.hasUnit(selectedMTile))
				choiceCenter.displayUnitUpgradeChoice(uts);
			else
				throw new IllegalStateException("Trying to display choices, but no tile is selected");
		}
		
		Collection<SharedActionType> ats = possibleActions.getUnitActions();
		if (ats != null && !ats.isEmpty()) {
			choiceCenter.displayUnitActionChoice(ats);
		}
		
		Collection<mw.shared.Coordinates> firableTiles = possibleActions.getFirableTiles();
		if (firableTiles != null && !firableTiles.isEmpty()) {
			DisplayUpdater.showFireButton(true);
		}
	}
	

	
	
}
