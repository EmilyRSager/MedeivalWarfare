package mw.shared.clientcommands;


public class DisplayPossibleActionsCommand extends AbstractClientCommand {
	private final String aType = "DisplayPossibleActionsCommand";
	private CollectionOfPossibleActions aPossibleActions;
	
	/**
	 * Constructor
	 * @param pPossibleActions
	 */
	public DisplayPossibleActionsCommand(CollectionOfPossibleActions pPossibleActions) {
		aPossibleActions = pPossibleActions;
	}
	
	
	@Override
	public boolean isValid() {
		return true;
	}

	@Override
	public void execute() {
		//TODO
		
		/**
		 * 1. Move Unit -> needs destination SharedCoordinates
		 * 2. Upgrade Village -> needs SharedTile.StrucureType
		 * 3. Hire Villager -> needs SharedTile.UnitType
		 */
	}

}
