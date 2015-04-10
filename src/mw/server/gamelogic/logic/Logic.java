package mw.server.gamelogic.logic;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import mw.server.gamelogic.enums.ActionType;
import mw.server.gamelogic.enums.Color;
import mw.server.gamelogic.enums.StructureType;
import mw.server.gamelogic.enums.UnitType;
import mw.server.gamelogic.enums.VillageType;
import mw.server.gamelogic.state.Game;
import mw.server.gamelogic.state.GameMap;
import mw.server.gamelogic.state.Player;
import mw.server.gamelogic.state.Tile;
import mw.server.gamelogic.state.Unit;
import mw.server.gamelogic.state.Village;

/**
 * @author Emily Sager, Charlie Bloomfield, Abhishek Gupta, Arthur Denefle, Hugo Kapp

 */
public class Logic {

	/**
	 * 
	 * @param pUnit
	 * @param pTile
	 * @return
	 */
	public static ArrayList<ActionType> getPossibleActions(Unit pUnit, Tile pTile)
	{
		ArrayList< ActionType> possActions = new ArrayList<ActionType>(); 

		UnitType pUnitType = pUnit.getUnitType(); 
		ActionType pActionType = pUnit.getActionType(); 
		{
			//Empty Tile Cases
			if (pTile.getStructureType() == StructureType.NO_STRUCT)
			{
				if (pUnitType == UnitType.PEASANT)
				{
					if (pActionType == ActionType.READY)
					{
						possActions.add(ActionType.BUILDINGROAD);
						possActions.add(ActionType.CULTIVATING_BEGIN);
					}
				}

			}

		}
		return possActions; 
	}

	/**
	 * Does all the logic calculations resulting from moving a unit
	 * @param startTile
	 * @param pDestinationTile
	 * @param pGame
	 * @param pGameMap
	 */
	public static boolean updateGameState(Unit crtUnit, Tile startTile, Tile pDestinationTile, Game pGame, GameMap pGameMap)
	{
		boolean tookOverTile = false; 
		UnitType crtUnitType = crtUnit.getUnitType(); 
	 
		if (tilesAreSameColor(startTile, pDestinationTile))
		{	
			System.out.println("[Game] Moving unit within village. ");
			switch (crtUnitType)
			{
			case PEASANT:
				movePeasant(crtUnit, startTile, pDestinationTile, pGame, pGameMap);
				break;
			case INFANTRY: 
				moveInfantry(crtUnit, startTile, pDestinationTile, pGame, pGameMap); 
				break;
			case SOLDIER: 
				moveSoldier(crtUnit, startTile, pDestinationTile, pGameMap);
				break; 
			case KNIGHT:
				moveKnight(crtUnit, startTile, pDestinationTile, pGameMap);
			case CANNON:
				moveCannon(crtUnit, startTile, pDestinationTile, pGameMap);
				break;
			default:
				break;
			}
		}
		else 
		{
			pGame.takeoverEnemyTile(startTile, pDestinationTile);
			tookOverTile = true; 
		}
		return tookOverTile;
	}
	
	private static void moveCannon(Unit crtUnit, Tile startTile, Tile pDestinationTile, GameMap pGameMap)
	{

		switch (pDestinationTile.getStructureType())
		{
		case TREE:
		case TOMBSTONE: 
		case WATCHTOWER: 
		case VILLAGE_CAPITAL: 
			throw new IllegalArgumentException("Trying to move a cannon over a "+pDestinationTile.getStructureType());
			
		case ROAD: 
			pDestinationTile.setUnit(crtUnit);
			startTile.setUnit(null);
			crtUnit.setActionType(ActionType.MOVED);
			break;
			
		case NO_STRUCT:
			pDestinationTile.setUnit(crtUnit);
			startTile.setUnit(null);
			crtUnit.setActionType(ActionType.MOVED);
			if (pDestinationTile.isMeadowOnTile())
			{
				pDestinationTile.setMeadow(false);
			}
			break;
			
		default:
			throw new IllegalArgumentException("The value "+pDestinationTile.getStructureType()+" is not recognized as a StructureType to move a cannon on");
		}

	}

	/**
	 * Moves a soldier to a destination tile 
	 * @param crtUnit
	 * @param startTile
	 * @param pDestinationTile
	 * @param pGameMap
	 */
	private static void moveSoldier(Unit crtUnit, Tile startTile, Tile pDestinationTile, GameMap pGameMap)
	{

		switch (pDestinationTile.getStructureType())
		{
		case TREE:
			Village crt = pGameMap.getVillage(startTile);
			if (crt !=null)
			{
				crt.addOrSubtractWood(1);
			}
			pDestinationTile.setUnit(crtUnit);
			pDestinationTile.setStructureType(StructureType.NO_STRUCT);
			startTile.setUnit(null);
			crtUnit.setActionType(ActionType.MOVED);
			break;

		case TOMBSTONE: 
			pDestinationTile.setStructureType(StructureType.NO_STRUCT);
			pDestinationTile.setUnit(crtUnit);
			startTile.setUnit(null);
			crtUnit.setActionType(ActionType.MOVED);
			break; 

		case ROAD: 
			pDestinationTile.setUnit(crtUnit);
			startTile.setUnit(null);
			crtUnit.setActionType(ActionType.READY);
			break; 

		case WATCHTOWER: 
			//TODO
			break;
		case VILLAGE_CAPITAL:
			//TODO 
			break;

		default:
			pDestinationTile.setUnit(crtUnit);
			startTile.setUnit(null);
			crtUnit.setActionType(ActionType.READY);
			if (pDestinationTile.isMeadowOnTile())
			{
				pDestinationTile.setMeadow(false);
			}
			break;
		}

	}

	/**
	 * Moves a knight to a given tile
	 * @param crtUnit
	 * @param startTile
	 * @param pDestinationTile
	 * @param pGameMap
	 */
	private static void moveKnight(Unit crtUnit, Tile startTile, Tile pDestinationTile, GameMap pGameMap)
	{
		switch (pDestinationTile.getStructureType())
		{
		case TREE:
			//do nothing-- this case shouldn't happen
			break; 
		case TOMBSTONE: 
			//do nothing-- this case shouldn't happen
			break; 
		case ROAD: 
			pDestinationTile.setUnit(crtUnit);
			startTile.setUnit(null);
			crtUnit.setActionType(ActionType.READY);
			break;
		case VILLAGE_CAPITAL: 
			//TODO 
			break; 
		case WATCHTOWER: 
			//TODO 
			break;
		default:
			pDestinationTile.setUnit(crtUnit);
			startTile.setUnit(null);
			crtUnit.setActionType(ActionType.READY);
			if (pDestinationTile.isMeadowOnTile())
			{
				pDestinationTile.setMeadow(false);
			}
			break;
		}
	}

	/**
	 * @param startTile
	 * @param pDestinationTile
	 * @param pGame
	 * @param pGameMap
	 * @pre crtUnit has type Peasant
	 */
	private static void movePeasant(Unit crtUnit, Tile startTile, Tile pDestinationTile, Game pGame, GameMap pGameMap){
		StructureType destStructureType = pDestinationTile.getStructureType();
		switch (destStructureType)
		{

		case TREE:
			Village crt = pGameMap.getVillage(startTile);
			if (crt !=null)
			{
				crt.addOrSubtractWood(1);
			}
			pDestinationTile.setUnit(crtUnit);
			pDestinationTile.setStructureType(StructureType.NO_STRUCT);
			startTile.setUnit(null);
			crtUnit.setActionType(ActionType.MOVED);
			//System.out.println("[Gamelogic] Action Type of Peasant set to moved");
			break;

		case TOMBSTONE: 
			pDestinationTile.setStructureType(StructureType.NO_STRUCT);
			pDestinationTile.setUnit(crtUnit);
			startTile.setUnit(null);
			crtUnit.setActionType(ActionType.MOVED);
			break; 

		case WATCHTOWER: 
			//TODO
			break;
		case VILLAGE_CAPITAL:
			//TODO 
			break;
		default: 
			pDestinationTile.setUnit(crtUnit);
			startTile.setUnit(null);
			crtUnit.setActionType(ActionType.READY); //If units move to a road or empty tile they can still move
			break;
		}
	}

	/**
	 * @param crtUnit
	 * @param startTile
	 * @param pDestinationTile
	 * @param pGame
	 * @param pGameMap
	 * @pre crtUnit has type Infantry
	 */
	private static void moveInfantry(Unit crtUnit, Tile startTile, Tile pDestinationTile, Game pGame, GameMap pGameMap){
		StructureType destStructType = pDestinationTile.getStructureType();
		switch (destStructType)
		{
		case TREE: 
			Village crt = pGameMap.getVillage(startTile);
			if (crt !=null)
			{
				crt.addOrSubtractWood(1);
			}
			pDestinationTile.setUnit(crtUnit);
			pDestinationTile.setStructureType(StructureType.NO_STRUCT);
			startTile.setUnit(null);
			crtUnit.setActionType(ActionType.MOVED);
			break;
		case TOMBSTONE: 
			pDestinationTile.setUnit(crtUnit);
			pDestinationTile.setStructureType(StructureType.NO_STRUCT);
			startTile.setUnit(null);
			crtUnit.setActionType(ActionType.MOVED);
		case ROAD: 
			pDestinationTile.setUnit(crtUnit);
			startTile.setUnit(null);
			crtUnit.setActionType(ActionType.READY);
			break; 
		case WATCHTOWER: 
			//TODO
			break;
		case VILLAGE_CAPITAL:
			//TODO 
			break;
		default:
			pDestinationTile.setUnit(crtUnit);
			startTile.setUnit(null);
			crtUnit.setActionType(ActionType.READY);
			/*if (pDestinationTile.isMeadowOnTile())
			{
				pDestinationTile.setMeadow(false);
			}*/
			break;
		}
	}



	/**
	 * @param pTile
	 * @return
	 */
	private static boolean isNeutral (Tile pTile)
	{
		return pTile.getColor() == Color.NEUTRAL;
	}

	/**
	 * 
	 * @param pTile
	 * @param pTile2
	 * @return
	 */
	private static boolean tilesAreSameColor(Tile pTile, Tile pTile2)
	{
		return pTile.getColor() == pTile2.getColor();
	}

	public static void checkFuse(Tile pStartTile, Tile pDestTile, Game pGame) 
	{
		
		Player pCurrentPlayer = pGame.getCurrentPlayer(); 
		Village startVillage = pGame.getVillage(pStartTile); 
		startVillage.addTile(pDestTile);
		Collection<Tile> pNeighbors = pGame.getNeighbors(pDestTile); 
		Tile startCapital = startVillage.getCapital(); 
		Collection<Village> toFuse = new HashSet<Village>(); 
		boolean needToFuse = false; 
		VillageType highestVillage = startCapital.getVillageType();
		for (Tile lTile : pNeighbors)
		{
			if (lTile.getColor() == startVillage.getColor())
			{
				if (!pGame.getVillage(lTile).equals(startVillage))
				{
					toFuse.add(pGame.getVillage(lTile)); 
					Tile tmpCapital = pGame.getVillage(lTile).getCapital();
					if(tmpCapital.getVillageType().ordinal()> startCapital.getVillageType().ordinal())
					{
						highestVillage = tmpCapital.getVillageType();
						//startCapital.setStructureType(StructureType.NO_STRUCT);
						//startCapital.setVillageType(VillageType.NO_VILLAGE);
						//startCapital.notifyChanged();
						//startCapital = tmpCapital;
					}
					pCurrentPlayer.removeVillage(pGame.getVillage(lTile));
					needToFuse = true;
				}
			}
		}
		if (needToFuse)
		{
			System.out.println("[Game] A Village Fuse is required... attempting to fuse villages.");
			pGame.fuseVillages(toFuse, startCapital, pCurrentPlayer, highestVillage);
		}
	}

}
