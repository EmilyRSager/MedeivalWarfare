package mw.shared;

import java.util.Collection;

public class SharedPossibleGameActions {

	private Collection<Coordinates> possibleMoves;
	private Collection<SharedTile.UnitType> possibleUnitHireUpgrade;
	private Collection <SharedActionType>  possibleUnitActions;	// not required for the demo
	private Collection<Coordinates> aCombinableUnitTiles;
	private Collection<Coordinates> aHirableUnitTiles;
	private Collection<Coordinates> aFirableTiles;
	private boolean canBuildWatchtower; 
	SharedTile.VillageType upgradableVillageType;


	/* ========================
	 * 		Constructors
	 * ========================
	 */
	
	public SharedPossibleGameActions (Collection<Coordinates> moves, Collection<SharedTile.UnitType> pUnitTypes, Collection<SharedActionType> pActionTypes, SharedTile.VillageType pVillageType, Collection<Coordinates> pCombinable, Collection<Coordinates> pHirable, Collection<Coordinates> pFirable, boolean pWatchtower)
	{
		possibleMoves = moves;
		possibleUnitHireUpgrade = pUnitTypes;
		possibleUnitActions = pActionTypes; 
		upgradableVillageType = pVillageType;
		aCombinableUnitTiles = pCombinable;
		aHirableUnitTiles = pHirable;
		aFirableTiles = pFirable;
		canBuildWatchtower = pWatchtower;
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
	
	public Collection<Coordinates> getCombinableUnitTiles()
	{
		return aCombinableUnitTiles;
	}
	
	public Collection<Coordinates> getHirableUnitTiles()
	{
		return aHirableUnitTiles;
	}
	
	public Collection<Coordinates> getFirableTiles()
	{
		return aFirableTiles;
	}
 }
