package mw.client.network;

import mw.shared.SharedActionType;
import mw.shared.SharedCoordinates;
import mw.shared.SharedTile;
import mw.shared.servercommands.AuthenticateUserCommand;
import mw.shared.servercommands.GetPossibleGameActionsCommand;
import mw.shared.servercommands.HireUnitCommand;
import mw.shared.servercommands.MoveUnitCommand;
import mw.shared.servercommands.RequestNewGameCommand;
import mw.shared.servercommands.SetActionTypeCommand;
import mw.shared.servercommands.UpgradeUnitCommand;
import mw.shared.servercommands.UpgradeVillageCommand;

/**
 * Provides access to the network so clients can send commands to the Game Server.
 */
public class NetworkController {
	private static ServerChannel aServerChannel;
	
	/**
	 * initializes aServerChannel
	 * @param pServerChannel
	 */
	public static void initializeServerChannel(ServerChannel pServerChannel){
		aServerChannel = pServerChannel;
	}
	
	/**
	 * 
	 * @param pUsername
	 * @param pPassword
	 */
	public static void authenticateUser(String pUsername, String pPassword){
		aServerChannel.sendCommand(new AuthenticateUserCommand(pUsername, pPassword));
	}
	
	
	/**
	 * 
	 */
	public static void requestNewGame(){
		aServerChannel.sendCommand(new RequestNewGameCommand());
	}
	
	/**
	 * 
	 * @param pUnitCoordinates
	 * @param pActionType
	 */
	public static void setActionType(SharedCoordinates pUnitCoordinates, SharedActionType pActionType){
		aServerChannel.sendCommand(new SetActionTypeCommand(pUnitCoordinates, pActionType));
	}
	
	/**
	 * 
	 * @param pUnitCoordinates
	 * @param pUnitType
	 */
	public static void hireUnit(SharedCoordinates pUnitCoordinates, SharedTile.UnitType pUnitType){
		aServerChannel.sendCommand(new HireUnitCommand(pUnitCoordinates, pUnitType));
	}
	
	/**
	 * 
	 * @param pUnitCoordinates
	 * @param pUnitType
	 */
	public static void upgradeUnit(SharedCoordinates pUnitCoordinates, SharedTile.UnitType pUnitType){
		aServerChannel.sendCommand(new UpgradeUnitCommand(pUnitCoordinates, pUnitType));
	}
	
	/**
	 * 
	 * @param pVillageCoordinates
	 * @param pVillageType
	 */
	public static void upgradeVillage(SharedCoordinates pVillageCoordinates, SharedTile.VillageType pVillageType){
		aServerChannel.sendCommand(new UpgradeVillageCommand(pVillageCoordinates, pVillageType));
	}
	
	/**
	 * 
	 * @param pSourceCoordinates
	 * @param pDestinationCoordinates
	 */
	public static void moveUnit(SharedCoordinates pSourceCoordinates, SharedCoordinates pDestinationCoordinates){
		aServerChannel.sendCommand(new MoveUnitCommand(pSourceCoordinates, pDestinationCoordinates));
	}
	
	/**
	 * Does something
	 * @param pAbstractServerCommand
	 */
	public static void getPossibleGameActions(SharedCoordinates pSharedCoordinates){
		aServerChannel.sendCommand(new GetPossibleGameActionsCommand(pSharedCoordinates));
	}
}
