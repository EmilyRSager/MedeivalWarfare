package mw.client.controller.menuing;

import java.util.Collection;

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
	
	
	private static BlockingFuture<Collection<String>> gameNamesResults;
	
	public static Collection<String> getJoinableGames()
	{
		gameNamesResults = new BlockingFuture<Collection<String>>();
		NetworkController.askForJoinableGames();
		return gameNamesResults.getValue();
	}
	
	public static void setJoinableGamesResult(Collection<String> result)
	{
		gameNamesResults.setValue(result);
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
		NetworkController.createGame(gameName, numberPlayers);
		return gameCreationResult.getValue();
	}
	
	public static void setGameCreationResult(boolean result)
	{
		gameCreationResult.setValue(result);
	}
	
}