package mw.client.controller.menuing;

import mw.client.network.NetworkController;
import mw.shared.SharedCreatedGame;
import mw.shared.SharedGameLobby;
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
	
	
	
	private static BlockingFuture<SharedGameLobby> joinableGamesResult;
	
	public static SharedGameLobby getJoinableGames()
	{
		joinableGamesResult = new BlockingFuture<SharedGameLobby>();
		NetworkController.askForJoinableGames();
		return joinableGamesResult.getValue();
	}
	
	public static void setJoinableGamesResult(SharedGameLobby result)
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
	

	
	
	private static BlockingFuture<SharedCreatedGame> gameCreationResult;
	
	public static SharedCreatedGame tryCreateGame(String gameName, int numberPlayers)
	{
		gameCreationResult = new BlockingFuture<SharedCreatedGame>();
		NetworkController.requestNewGame(gameName, numberPlayers);
		return gameCreationResult.getValue();
	}
	
	public static void setGameCreationResult(SharedCreatedGame result)
	{
		gameCreationResult.setValue(result);
	}
	
	
	
	public static void leaveGameRoom()
	{
		//NetworkController.leaveCurrentGameRoom();
	}
}