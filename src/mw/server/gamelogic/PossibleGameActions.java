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
	private VillageType aUpgradableVillage;
	private boolean aCanBuildWatchtower; 
	private Collection<Tile> aCombinableUnitTiles; 
	private Collection<Tile> aHirableUnitTiles; 
	private Collection<Tile> aFirableTiles;

	public PossibleGameActions (Collection<Tile> pMovable, Collection<UnitType> pUnitTypes, Collection<ActionType> pActionTypes, VillageType pVillageType, boolean pWatchtower, Collection<Tile> pCombinable, Collection<Tile> pHirable, Collection<Tile> pFirable)
	{
		aCanBuildWatchtower = pWatchtower; 
		aMovable = pMovable; 
		aUpgradableUnits = pUnitTypes;
		aPossibleActions = pActionTypes; 
		aUpgradableVillage = pVillageType; 
		aCombinableUnitTiles = pCombinable; 
		aHirableUnitTiles = pHirable; 
		aFirableTiles = pFirable;
	}

	public Collection<Tile> getHirableUnitTiles() 
	{
		return aHirableUnitTiles;
	}
	public Collection<Tile> getCombinableUnitTiles()
	{
		return aCombinableUnitTiles; 
	}

	public boolean getCanBuildWatchtower()
	{
		return aCanBuildWatchtower; 
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

