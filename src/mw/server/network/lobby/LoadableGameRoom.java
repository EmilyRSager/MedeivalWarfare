/**
 * @author Abhishek Gupta
 */

package mw.server.network.lobby;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;
import java.util.UUID;

import test.mw.server.gamelogic.SaveGame;
import mw.server.admin.Account;
import mw.server.admin.AccountGameInfo;
import mw.server.admin.AccountManager;
import mw.server.gamelogic.controllers.GameController;
import mw.server.gamelogic.enums.Color;
import mw.server.gamelogic.exceptions.TooManyPlayersException;
import mw.server.gamelogic.state.Game;
import mw.server.gamelogic.state.GameID;
import mw.server.gamelogic.state.Player;
import mw.server.gamelogic.state.Tile;
import mw.server.network.communication.ClientCommunicationController;
import mw.server.network.controllers.GameInitializationController;
import mw.server.network.controllers.GameStateCommandDistributor;
import mw.server.network.mappers.GameMapper;
import mw.server.network.mappers.PlayerMapper;
import mw.shared.clientcommands.NotifyBeginTurnCommand;
import mw.util.MultiArrayIterable;
import mw.util.Tuple2;

public class LoadableGameRoom extends GameRoom{

	//the attribute helps to ensure that only the correct accounts can join the game
	private GameID aGameID;


	public GameID returnGameID(){
		return aGameID;
	}
	public LoadableGameRoom(int pNumRequestedClients, GameID pGameID) {

		super(pNumRequestedClients);
		this.aGameID = pGameID;


	}

	public void addAllowableClient(UUID pAccountUUID){

		if (aGameID.getaListOfAccountUUIDs().contains(pAccountUUID)) {
			this.aWaitingClients.add(pAccountUUID);
		}
		else {
			throw new IllegalArgumentException("you are not allowed to join this game, exit now");
		}

	}

	@Override
	public void initializeGame(String pGameName){

		System.out.println("[Server] Initializing loaded game.");

		Game lGame = aGameID.getaGame();
		int lNumPlayers = lGame.getPlayers().size();
		GameMapper.getInstance().putGame(aWaitingClients, lGame); //add clients to Game Mapping

		//map clients to players
		Collection<Player> lPlayers = lGame.getPlayers();
		assignAccountsToPlayers(aWaitingClients, lPlayers);

		for (UUID accountUUID : aWaitingClients) {
			Account lAccount = AccountManager.getInstance().getAccount(accountUUID);
			AccountGameInfo lAccountGameInfo = lAccount.getaAccountGameInfo();
			Color playerColor = PlayerMapper.getInstance().getPlayer(accountUUID).getPlayerColor();
			lAccountGameInfo.setCurrentGame(new Tuple2<String, Color>(aGameID.getaName(), playerColor ));
			lAccountGameInfo.addToActiveGames(lAccountGameInfo.getCurrentGame());
			AccountManager.getInstance().saveAccountData(lAccount);
		}
		try {
			SaveGame.SaveMyGame(aGameID);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//Inform client that it is his turn
		UUID lCurrentAccountID = PlayerMapper.getInstance().getAccount(GameController.getCurrentPlayer(lGame));
		ClientCommunicationController.sendCommand(lCurrentAccountID, new NotifyBeginTurnCommand());

	}
	
	@Override
	protected void assignAccountsToPlayers(Set<UUID> pAccountIDs, Collection<Player> pPlayers){
		for(UUID account: pAccountIDs){
			Color lColor = AccountManager.getInstance().getAccount(account).getaAccountGameInfo().getCurrentGame().getVal2();
			Player lPlayer = new Player(); 
			for(Player p: pPlayers){
				if (p.getPlayerColor()==lColor) {
					lPlayer=p;
				}
			}
			PlayerMapper.getInstance().putPlayer(account, lPlayer);
		}
	}
}
