/**
 * @author Charlie Bloomfield
 * Feb 21, 2015
 */

package mw.server.network;

import java.util.Set;

import mw.server.gamelogic.Tile;
import mw.shared.clientcommands.AbstractClientCommand;
import mw.shared.clientcommands.NewGameCommand;
import mw.shared.clientcommands.UpdateTileCommand;

/**
 * Distributes GameState updates to the clients participating with an instance of a single game. 
 * There will be one instance of this class observing each game that exists on the server.
 */
public class GameStateCommandDistributor implements IGameStateObserver {
	private Set<Integer> aClientIDs; //the set of clients who are participating in a Game instance
	
	/**
	 * Constructor.
	 * @param a set of ClientIDs to be notified of changes to GameState
	 */
	public GameStateCommandDistributor(Set<Integer> pClientIDs) {
		aClientIDs = pClientIDs;
	}

	/**
	 * Sends changed information about pTile to the set of clients involved in this game instance
	 * @param pSharedTile, the tile whose state has changed in the Model
	 * @return void
	 */
	@Override
	public void updateTile(Tile pTile) {
		AbstractClientCommand lClientCommand = 
				new UpdateTileCommand(SharedTileTranslator.translateTile(pTile));
		
		distributeMessage(lClientCommand);
	}
	
	/**
	 * Sends new game information about pGameMap to the set of clients involved in this game instance
	 * @param pGameMap
	 */
	public void newGame(Tile[][] pGameMap){
		
		AbstractClientCommand lClientCommand = 
				new NewGameCommand(SharedTileTranslator.translateMap(pGameMap));
		
		distributeMessage(lClientCommand);
	}
	
	/**
	 * Invokes sendMessage on each client in the set aClientIDs. 
	 * @param pClientMessage
	 * @return void
	 */
	private void distributeMessage(AbstractClientCommand pClientCommand){
		for(ClientChannel lClientOnServer : ClientChannelManager.getInstance().getChannelSet(aClientIDs)){
			lClientOnServer.sendCommand(pClientCommand);
		}
	}
}
