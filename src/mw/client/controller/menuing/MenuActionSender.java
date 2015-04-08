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
		//return new SharedGameLobby(new HashSet<SharedCreatedGame>());
		joinableGamesResult = new BlockingFuture<SharedGameLobby>();
		NetworkController.askForJoinableGames();
		return joinableGamesResult.getValue();
	}
	
	public static void setJoinableGamesResult(SharedGameLobby result)
	{
		joinableGamesResult.setValue(result);
	}
	
	
	
	
	private static BlockingFuture<SharedCreatedGame> joinedGameResult;
	
	public static SharedCreatedGame tryJoiningGame(String gameName)
	{
		joinedGameResult = new BlockingFuture<SharedCreatedGame>();
		NetworkController.joinGame(gameName);
		return joinedGameResult.getValue();
	}
	
	public static SharedCreatedGame tryCreateGame(String gameName, int numberPlayers)
	{
		joinedGameResult = new BlockingFuture<SharedCreatedGame>();
		NetworkController.requestNewGame(gameName, numberPlayers);
		return joinedGameResult.getValue();//gameCreationResult.getValue();
	}
	
	public static void setJoinedGameResult(SharedCreatedGame result)
	{
		if (joinableGamesResult != null)
			joinedGameResult.setValue(result);
		else
			System.out.println("[MenuActionSender] trying to test the game huh ?");
	}
	
	
	public static void displayGameInvite(SharedCreatedGame game)
	{
		ScreenSwitcher.openInvite(game);
	}
	
	public static void leaveGameRoom()
	{
		//NetworkController.leaveCurrentGameRoom();
	}
}