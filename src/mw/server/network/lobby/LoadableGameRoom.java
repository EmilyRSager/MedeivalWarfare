/**
 * @author Abhishek Gupta
 */

package mw.server.network.lobby;

import java.io.IOException;
import java.util.Collection;
import java.util.Set;
import java.util.UUID;

import test.mw.server.gamelogic.SaveGame;
import mw.server.admin.Account;
import mw.server.admin.AccountGameInfo;
import mw.server.admin.AccountManager;
import mw.server.gamelogic.controllers.GameController;
import mw.server.gamelogic.enums.Color;
import mw.server.gamelogic.state.Game;
import mw.server.gamelogic.state.GameID;
import mw.server.gamelogic.state.Player;
import mw.server.network.communication.ClientCommunicationController;
import mw.server.network.controllers.GameStateCommandDistributor;
import mw.server.network.mappers.GameMapper;
import mw.server.network.mappers.PlayerMapper;
import mw.server.network.translators.SharedTileTranslator;
import mw.shared.clientcommands.NotifyBeginTurnCommand;
import mw.shared.clientcommands.SetColorCommand;
import mw.util.Tuple2;

public class LoadableGameRoom extends GameRoom{

	//the attribute helps to ensure that only the correct accounts can join the game
	private GameID aGameID;

	/**
	 * Constructor
	 * @param pNumRequestedClients
	 * @param pGameID
	 */
	public LoadableGameRoom(int pNumRequestedClients, GameID pGameID) {
		super(pNumRequestedClients);
		this.aGameID = pGameID;
	}
	
	/**
	 * @return
	 */
	public GameID returnGameID(){
		return aGameID;
	}

	/**
	 * 
	 * @param pAccountUUID
	 */
	@Override
	public void addClient(UUID pAccountUUID) throws IllegalArgumentException{
		if (aGameID.getaListOfAccountUUIDs().contains(pAccountUUID)) {
			this.aWaitingClients.add(pAccountUUID);
		}
		else {
			throw new IllegalArgumentException("You are not allowed to join this game, please choose another game or create a nwe one.");
		}
	}

	/**
	 * 
	 * @param the name of the game to be created
	 */
	@Override
	public void initializeGame(String pGameName){
		System.out.println("[Server] Initializing loaded game.");

		Game lGame = aGameID.getaGame();
		GameMapper.getInstance().putGame(aWaitingClients, lGame); //add clients to Game Mapping

		GameStateCommandDistributor lGameStateCommandDistributor = 
				new GameStateCommandDistributor(aWaitingClients, lGame);
		attachObservable(lGame, lGameStateCommandDistributor);
		lGameStateCommandDistributor.newGame(lGame.getGameTiles());
		
		//map clients to players
		Collection<Player> lPlayers = lGame.getPlayers();
		assignAccountsToPlayers(aWaitingClients, lPlayers);

		for (UUID accountUUID : aWaitingClients) {
			Account lAccount = AccountManager.getInstance().getAccount(accountUUID);
			AccountGameInfo lAccountGameInfo = lAccount.getAccountGameInfo();
			Color playerColor = PlayerMapper.getInstance().getPlayer(accountUUID).getPlayerColor();
			lAccountGameInfo.setCurrentGame(new Tuple2<String, Color>(aGameID.getaName(), playerColor ));
			lAccountGameInfo.addToActiveGames(lAccountGameInfo.getCurrentGame());
			AccountManager.getInstance().saveAccountData(lAccount);
		}
		try {
			SaveGame.SaveMyGame(aGameID);
		} catch (IOException e) {
			e.printStackTrace();
		}

		//Inform client that it is his turn
		UUID lCurrentAccountID = PlayerMapper.getInstance().getAccount(GameController.getCurrentPlayer(lGame));
		ClientCommunicationController.sendCommand(lCurrentAccountID, new NotifyBeginTurnCommand());
	}
	
	/**
	 * 
	 */
	@Override
	protected void assignAccountsToPlayers(Set<UUID> pAccountIDs, Collection<Player> pPlayers){
		for(UUID account : pAccountIDs){
			Color lColor = AccountManager.getInstance().getAccount(account).getAccountGameInfo().getCurrentGame().getVal2();
			Player lPlayer = new Player(); 
			for(Player p : pPlayers){
				if (p.getPlayerColor() == lColor) {
					lPlayer = p;
				}
			}
			PlayerMapper.getInstance().putPlayer(account, lPlayer);
			ClientCommunicationController.sendCommand(account, new SetColorCommand(SharedTileTranslator.translateColor(lColor)));
		}
	}
}