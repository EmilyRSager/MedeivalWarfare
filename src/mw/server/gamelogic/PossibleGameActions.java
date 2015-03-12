package mw.server.gamelogic;

import java.util.Collection;

import mw.server.gamelogic.enums.ActionType;
import mw.server.gamelogic.enums.UnitType;
import mw.server.gamelogic.enums.VillageType;
import mw.server.gamelogic.state.Tile;

public class PossibleGameActions
{
	private Collection<Tile> aMovable; 
	private Collection<UnitType> aUpgradableUnits;   
	private Collection <ActionType>  aPossibleActions; 
	VillageType aUpgradableVillage;

	public PossibleGameActions (Collection<Tile> pMovable, Collection<UnitType> pUnitTypes, Collection<ActionType> pActionTypes, VillageType pVillageType)
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

