package mw.shared;

import java.util.Collection;

public class SharedPossibleGameActions {

	private Collection<Coordinates> possibleMoves;
	private Collection<SharedTile.UnitType> possibleUnitHireUpgrade;
	private Collection <SharedActionType>  possibleUnitActions;	// not required for the demo
	SharedTile.VillageType upgradableVillageType;


	/* ========================
	 * 		Constructors
	 * ========================
	 */
	
	public SharedPossibleGameActions (Collection<Coordinates> moves, Collection<SharedTile.UnitType> pUnitTypes, Collection<SharedActionType> pActionTypes, SharedTile.VillageType pVillageType)
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
	
	public Collection<Coordinates> getMovableTiles()
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