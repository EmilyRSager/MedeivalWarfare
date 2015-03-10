package mw.client.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import mw.shared.SharedActionType;
import mw.shared.SharedTile;
import mw.shared.SharedTile.VillageType;

public class ChoiceCenter {
	
	public enum ChoiceType { UNIT_HIRE, VILLAGE_UPGRADE, UNIT_ACTION } ;
	
	private static final String vupChoiceName = "Select the type of village to upgrade to:";
	private UserChoice<SharedTile.VillageType> villageUpgradeChoice;

	private static final String uactChoiceName = "Select an action:";
	private UserChoice<SharedActionType> unitActionChoice;

	private static final String uhireupChoiceName = "Select a type of unit to hire:";
	private UserChoice<SharedTile.UnitType> unitUpgradeHireChoice;
	
	/* ========================
	 * 		Constructors
	 * ========================
	 */

	public ChoiceCenter()
	{
		clear();
	}

	/* ==========================
	 * 		Public methods
	 * ==========================
	 */

	public void clear() 
	{
		villageUpgradeChoice = null;
		unitActionChoice = null;
		unitUpgradeHireChoice = null;
	}
	
	public void handleChoiceResult(String choiceTitle, String choseItem, ActionInterpreter handler)
	{
		if (choiceTitle.equals(vupChoiceName))
		{
			VillageType vt = getChoiceResult(villageUpgradeChoice, choseItem);
			handler.notifyVillageUpgradeChoiceResult(vt);
		}
		else if (choiceTitle.equals(uhireupChoiceName))
		{
			SharedTile.UnitType ut = getChoiceResult(unitUpgradeHireChoice, choseItem);
			handler.notifyUnitHireChoiceResult(ut);
		}
		else if (choiceTitle.equals(uactChoiceName))
		{
			SharedActionType at = getChoiceResult(unitActionChoice, choseItem);
			handler.notifyUnitActionChoiceResult(at);
		}
		else {
			throw new IllegalArgumentException("The pair (choiceTitle=\""+choiceTitle+"\", choseItem=\""+choseItem+") is invalid");
		}
	}
	
	
	//		Display


	public void displayVillageChoice(VillageType vt)
	{
		List<VillageType> vtList = new ArrayList<VillageType>();
		vtList.add(vt);
		villageUpgradeChoice = new UserChoice<SharedTile.VillageType>(vtList);
		DisplayUpdater.displayChoice(vupChoiceName, villageUpgradeChoice.itemsToString());
	}

	public void displayUnitTypeChoice(Collection<SharedTile.UnitType> ut)
	{
		unitUpgradeHireChoice = new UserChoice<SharedTile.UnitType>(ut);
		DisplayUpdater.displayChoice(uhireupChoiceName, unitUpgradeHireChoice.itemsToString());
	}

	public void displayUnitActionChoice(Collection<SharedActionType> unitActions)
	{
		unitActionChoice = new UserChoice<SharedActionType>(unitActions);
		DisplayUpdater.displayChoice(uactChoiceName, unitActionChoice.itemsToString());
	}
	
	/* ==========================
	 * 		Private methods
	 * ==========================
	 */

	private <T> T getChoiceResult(UserChoice<T> choice, String strRes)
	{
		if (choice == null)
			throw new IllegalArgumentException("No choice available, impossible to select anything");
		return choice.getItem(strRes);
	}

	/* ==========================
	 * 		Inherited methods
	 * ==========================
	 */



	/* ========================
	 * 		Static methods
	 * ========================
	 */
	
	public static String getChoiceTitle(ChoiceType choice)
	{
		switch (choice)
		{
		case UNIT_ACTION:
			return uactChoiceName;
			
		case UNIT_HIRE:
			return uhireupChoiceName;
			
		case VILLAGE_UPGRADE:
			return vupChoiceName;
			
			default:
				throw new IllegalArgumentException("The value "+choice+" does not have a choice name associated with it");
		}
	}

}