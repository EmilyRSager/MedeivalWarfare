package mw.server.gamelogic.logic;

import java.util.ArrayList;


import mw.server.gamelogic.enums.ActionType;
import mw.server.gamelogic.enums.Color;
import mw.server.gamelogic.enums.StructureType;
import mw.server.gamelogic.enums.UnitType;

import mw.server.gamelogic.state.Game;
import mw.server.gamelogic.state.GameMap;
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
		if (isNeutral(pDestinationTile))
		{
			pGame.takeoverNeutralTile(startTile, pDestinationTile);
			tookOverTile = true;
		} 
		if (tilesAreSameColor(startTile, pDestinationTile))
		{	
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
			if (pDestinationTile.isMeadowOnTile())
			{
				pDestinationTile.setMeadow(false);
			}
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

}