package mw.shared;

import java.util.Collection;

public class PossibleGameActions {

	private Collection<SharedCoordinates> possibleMoves; 
	private Collection<SharedTile.UnitType> possibleUnitHireUpgrade;
	private Collection <SharedActionType>  possibleUnitActions;	// not required for the demo
	SharedTile.VillageType upgradableVillageType;


	/* ========================
	 * 		Constructors
	 * ========================
	 */
	
	public PossibleGameActions (Collection<SharedCoordinates> moves, Collection<SharedTile.UnitType> pUnitTypes, Collection<SharedActionType> pActionTypes, SharedTile.VillageType pVillageType)
	{
		possibleMoves = moves;
		possibleUnitHireUpgrade = pUnitTypes;
		possibleUnitActions = pActionTypes; 
		upgradableVillageType = pVillageType;
	}


	/* ==========================
	 * 		Public methods
	 * ==========================
	 */
	
	public Collection<SharedCoordinates> getMovableTiles()
	{
		return possibleMoves;
	}
	
	public Collection<SharedTile.UnitType> getUnitUpgrade()
	{
		return possibleUnitHireUpgrade;
	}
	
	public Collection<SharedActionType> getUnitActions ()
	{
		return possibleUnitActions;
	}
	
	public SharedTile.VillageType getVillageUpgrade()
	{
		return upgradableVillageType; 
	}

}