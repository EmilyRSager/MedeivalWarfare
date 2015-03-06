package mw.server.gamelogic;

import java.util.Collection;

public class CollectionOfPossibleActions
{
	private Collection<Tile> aMovable; 
	private Collection<UnitType> aUpgradableUnits;   
	private Collection <ActionType>  aPossibleActions; 
	VillageType aUpgradableVillage;
	
	public CollectionOfPossibleActions (Collection<Tile> pMovable, Collection<UnitType> pUnitTypes, Collection<ActionType> pActionTypes, VillageType pVillageType)
	{
		aMovable = pMovable; 
		aUpgradableUnits = pUnitTypes;
		aPossibleActions = pActionTypes; 
		aUpgradableVillage = pVillageType; 
	}
	
	public Collection<Tile> getMovableTiles()
	{
		return aMovable;
	}
	public Collection<UnitType> getUnitUpgrade()
	{
		return aUpgradableUnits; 
	}
	public Collection<ActionType> getActions ()
	{
		return aPossibleActions;
	}
	public VillageType getVillageUpgrade()
	{
		return aUpgradableVillage; 
	}
}
