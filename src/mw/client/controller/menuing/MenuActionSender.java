package mw.client.controller.menuing;

import java.util.Collection;
import java.util.Map;

import mw.client.network.NetworkController;
import mw.shared.servercommands.SetActionTypeCommand;
import mw.util.BlockingFuture;

public final class MenuActionSender {


	/* ========================
	 * 		Static methods
	 * ========================
	 */

	private static BlockingFuture<Boolean> authenticationResult;
	
	public static boolean tryLogin(String username, String password)
	{
		authenticationResult = new BlockingFuture<Boolean>();
		NetworkController.authenticateUser(username, password);
		return authenticationResult.getValue();
	}
	
	public static void setAuthenticationResult(boolean result)
	{
		authenticationResult.setValue(result);
	}
	
	
	
	
	private static BlockingFuture<Boolean> accountCreationResult;
	
	public static boolean tryCreateAccount(String username, String password)
	{
		accountCreationResult = new BlockingFuture<Boolean>();
		NetworkController.createAccount(username, password);
		return accountCreationResult.getValue();
	}
	
	public static void setAccountCreationResult(boolean result)
	{
		accountCreationResult.setValue(result);
	}
	
	
	
	
	private static BlockingFuture<GameLobby> joinableGamesResult;
	
	public static GameLobby getJoinableGames()
	{
		joinableGamesResult = new BlockingFuture<GameLobby>();
		NetworkController.askForJoinableGames();
		return joinableGamesResult.getValue();
	}
	
	public static void setJoinableGamesResult(GameLobby result)
	{
		joinableGamesResult.setValue(result);
	}
	
	
	
	
	private static BlockingFuture<Boolean> joinedGameResult;
	
	public static boolean tryJoiningGame(String gameName)
	{
		joinedGameResult = new BlockingFuture<Boolean>();
		NetworkController.joinGame(gameName);
		return joinedGameResult.getValue();
	}
	
	public static void setJoinedGameResult(boolean result)
	{
		joinedGameResult.setValue(result);
	}
	

	
	
	private static BlockingFuture<Boolean> gameCreationResult;
	
	public static boolean tryCreateGame(String gameName, int numberPlayers)
	{
		gameCreationResult = new BlockingFuture<Boolean>();
		NetworkController.requestNewGame(gameName, numberPlayers);
		return gameCreationResult.getValue();
	}
	
	public static void setGameCreationResult(boolean result)
	{
		gameCreationResult.setValue(result);
	}
	
	
	
	public static void leaveGameRoom()
	{
		NetworkController.leaveCurrentGameRoom();
	}
}