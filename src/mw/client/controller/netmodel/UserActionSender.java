package mw.client.controller.netmodel;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import mw.client.controller.guimodel.ActionInterpreter;
import mw.client.controller.model.ModelQuerier;
import mw.client.controller.translator.ModelToNetworkTranslator;
import mw.client.model.ModelTile;
import mw.client.network.NetworkController;
import mw.shared.SharedActionType;
import mw.shared.SharedCoordinates;
import mw.shared.SharedPossibleGameActions;
import mw.shared.SharedTile.UnitType;
import mw.shared.SharedTile.VillageType;

public class UserActionSender {

	
	/* ===============================
	 * 		Generic Controller
	 * ===============================
	 */
	
	
	private static UserActionSender singleton;
	
	public static void initialize()
	{
		singleton = new UserActionSender();
	}
	
	public static void clear()
	{
		singleton = null;
	}
	
	public static UserActionSender singleton()
	{
		return singleton;
	}
	
	
	/* ===============================
	 * 		UserActionSender 
	 * ===============================
	 */

	private final ReentrantLock possibleActionsLock = new ReentrantLock();
	private final Condition possibleActionsReady = possibleActionsLock.newCondition();
	
	private SharedPossibleGameActions possibleActions;
	private boolean waitingForActions;
	private boolean actionsReady;
	
	/* ========================
	 * 		Constructors
	 * ========================
	 */



	/* ==========================
	 * 		Public methods
	 * ==========================
	 */


	//		Possible actions queries
	
	
	public void askForPossibleMoves(ModelTile clickedTile)
	{
		clearPossibleActions();
		
		actionsReady = false;
		waitingForActions = true;
		SharedCoordinates targetCoord = getCoordinates(clickedTile);
		NetworkController.getPossibleGameActions(targetCoord);
	}
	
	
	public SharedPossibleGameActions getPossibleActions()
	{
		waitForActions();
		return possibleActions;
	}
	
	
	public void setPossibleActions(SharedPossibleGameActions actions)
	{
		possibleActionsLock.lock();
		
		if (waitingForActions)
		{
			possibleActions = actions;
			
			ClientSynchronization.gameLock.lock();
			ActionInterpreter.singleton().handleNewPossibleActions(actions);
			ClientSynchronization.gameLock.unlock();
			
			waitingForActions = false;
			actionsReady = true;
		}
		possibleActionsReady.signalAll();
		
		possibleActionsLock.unlock();
	}
	
	public void clearPossibleActions()
	{
		waitForActions();
		possibleActionsLock.lock();
		
		possibleActions = null;
		waitingForActions = false;
		actionsReady = true;
		
		possibleActionsLock.unlock();
	}
	
	
	//		Network queries

	
	public boolean tryMoveUnit(ModelTile src, ModelTile dest)
	{
		waitForActions();
		
		SharedCoordinates coordSrc = getCoordinates(src);
		SharedCoordinates coordDest = getCoordinates(dest);

		if (possibleActions.getMovableTiles().contains(coordDest))
		{
			NetworkController.moveUnit(coordSrc, coordDest);
			return true;
		}
		else
			return false;
	}

	public void sendUpgradeVillage(ModelTile villageTile, VillageType vt)
	{
		SharedCoordinates coord = getCoordinates(villageTile);
		NetworkController.upgradeVillage(coord, vt);
	}

	public void sendUnitHire(ModelTile unitTile, UnitType ut)
	{
		SharedCoordinates coord = getCoordinates(unitTile);
		NetworkController.hireUnit(coord, ut);
	}

	public void sendUnitAction(ModelTile unitTile, SharedActionType at)
	{
		SharedCoordinates coord = getCoordinates(unitTile);
		NetworkController.setActionType(coord, at);
	}

	public void sendUnitUpgrade(ModelTile unitTile, UnitType ut)
	{
		SharedCoordinates coord = getCoordinates(unitTile);
		NetworkController.upgradeUnit(coord, ut);
	}
	
	public void sendEndTurn()
	{
		NetworkController.endTurn();
	}
	
	/* ==========================
	 * 		Private methods
	 * ==========================
	 */

	
	private void waitForActions() {
		possibleActionsLock.lock();

		while (waitingForActions && !actionsReady)
		{
			ClientSynchronization.gameLock.unlock();
			try	{
				possibleActionsReady.await();
			}
			catch (InterruptedException e) {
				e.printStackTrace();
			}
			ClientSynchronization.gameLock.lock();
		}
		//waitingForActions = false;
		//actionsReady = true;

		possibleActionsLock.unlock();
	}
	
	
	/* ==========================
	 * 		Inherited methods
	 * ==========================
	 */



	/* ========================
	 * 		Static methods
	 * ========================
	 */
	
	private static SharedCoordinates getCoordinates(ModelTile mtile)
	{
		return ModelToNetworkTranslator.translateModelCoordinates(ModelQuerier.getCoordinates(mtile));
	}


}