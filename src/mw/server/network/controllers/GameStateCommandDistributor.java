/**
 * @author Charlie Bloomfield
 * Feb 21, 2015
 */

package mw.server.network.controllers;

import java.util.Collection;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;
import java.util.UUID;

import mw.server.admin.Account;
import mw.server.admin.AccountGameInfo;
import mw.server.admin.AccountManager;
import mw.server.gamelogic.enums.PlayerState;
import mw.server.gamelogic.state.Player;
import mw.server.gamelogic.state.Tile;
import mw.server.network.communication.ClientCommunicationController;
import mw.server.network.lobby.GameID;
import mw.server.network.lobby.GameLobby;
import mw.server.network.mappers.GameMapper;
import mw.server.network.mappers.PlayerMapper;
import mw.server.network.translators.LobbyTranslator;
import mw.server.network.translators.SharedTileTranslator;
import mw.shared.Coordinates;
import mw.shared.SharedTile;
import mw.shared.clientcommands.AbstractClientCommand;
import mw.shared.clientcommands.NewGameCommand;
import mw.shared.clientcommands.RelaunchLobbyCommand;
import mw.shared.clientcommands.UpdateAggregateTilesCommand;
import mw.util.MultiArrayIterable;

/**
 * Distributes GameState updates to the clients participating with an instance of a single game. 
 * There will be one instance of this class observing each game that exists on the server.
 */
public class GameStateCommandDistributor implements Observer {
	/**
	 * As of right now, this class passes a Game object to the SharedTileTranslator, which passes
	 * the Game to the TileController in the getWood and getGold methods. This seems like pretty
	 * bad work flow, but it's necessary as SharedTiles need Gold and Wood, 
	 */
	private GameID aGameID;

	private HashMap<Coordinates, SharedTile> modifiedTilesBuffer;

	/**
	 * Constructor.
	 * @param a set of AccountIDs to be notified of changes to GameState
	 */
	public GameStateCommandDistributor(GameID pGameID) {
		aGameID = pGameID;
		modifiedTilesBuffer = new HashMap<Coordinates, SharedTile>();
		attachToObservables();
	}

	/**
	 * Sends changed information about pTile to the set of clients involved in this game instance
	 * @param pSharedTile, the tile whose state has changed in the Model
	 */
	@Override
	public void update(Observable pObservable, Object pObject) {
		if(pObservable instanceof Tile){
			Tile lTile = (Tile) pObservable;
			currentlyActiveDistributor = this;
			SharedTile tileTranslation = SharedTileTranslator.translateTile(lTile, aGameID.getGame());
			modifiedTilesBuffer.put(tileTranslation.getCoordinates(), tileTranslation);
		}
		else if(pObservable instanceof Player){
			Player lPlayer = (Player) pObservable;
			handlePlayerChange(lPlayer);
		}
		else{
			//TODO
		}
	}

	/**
	 * Sends new game information about pGameMap to the set of clients involved in this game instance
	 * @param pGameMap
	 */
	public void sendNewGame() {
		SharedTile[][] lSharedTiles = SharedTileTranslator.translateMap(aGameID.getGame().getGameTiles(), aGameID.getGame());
		AbstractClientCommand lClientCommand = new NewGameCommand(lSharedTiles);
		distributeCommand(lClientCommand);
	}

	/**
	 * If a 
	 */
	private void handlePlayerChange(Player pPlayer){
		UUID lAccountID = PlayerMapper.getInstance().getAccount(pPlayer);
		Account lAccount = AccountManager.getInstance().getAccount(lAccountID);
		AccountGameInfo lAccountGameInfo = lAccount.getAccountGameInfo();

		if(pPlayer.getPlayerState() == PlayerState.LOST){
			//TODO deal with current game shit
			lAccountGameInfo.incrementLosses();
			ClientCommunicationController.sendCommand(lAccountID, 
					new RelaunchLobbyCommand(GameInitializationController.getSharedLobbyGameLobbyForClient(lAccountID), "You lost the game!"));
		}
		else{
			lAccountGameInfo.incrementWins();
			ClientCommunicationController.sendCommand(lAccountID, 
					new RelaunchLobbyCommand(GameInitializationController.getSharedLobbyGameLobbyForClient(lAccountID), "You won!"));
		}
		AccountManager.getInstance().saveAccountData(lAccount);
		TerminationController.removeMemoryMappings(lAccountID);
	}
	
	/**
	 * attaches this observer to all the values in the game that need observing
	 */
	private void attachToObservables(){
		//attach observer to each tile
		Tile[][] lGameTiles = aGameID.getGame().getGameTiles();
		for(Tile lTile : MultiArrayIterable.toIterable(lGameTiles)){
			//add observer to each tile
			lTile.addObserver(this);
		}
		
		//attach observer to each player
		for(Player lPlayer : aGameID.getGame().getPlayers()){
			lPlayer.addObserver(this);
		}
	}

	/**
	 * Distributes the changes to each tile in an aggregate update tile state command
	 */
	private void distributeAllCommands() {
		Collection<SharedTile> newTileStates = modifiedTilesBuffer.values();
		distributeCommand(new UpdateAggregateTilesCommand(newTileStates));
		modifiedTilesBuffer = new HashMap<Coordinates, SharedTile>();
	}

	/**
	 * Invokes sendCommand on each client in the set aAccountIDs. 
	 * @param pClientMessage
	 */
	private void distributeCommand(AbstractClientCommand pClientCommand) {
		for(UUID lUUID : aGameID.getParticipantAccountIDs()){
			ClientCommunicationController.sendCommand(lUUID, pClientCommand);
		}
	}

	/*
	 * Static 
	 */

	private static GameStateCommandDistributor currentlyActiveDistributor;

	public static void flushBuffer() {
		if (currentlyActiveDistributor != null) {
			currentlyActiveDistributor.distributeAllCommands();
			currentlyActiveDistributor = null;
		}
	}
}
