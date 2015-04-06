package mw.client.controller.menuing;

import java.util.Collection;

import mw.client.controller.menuing.ScreenSwitcher.ScreenKind;

public abstract class MenuControl {


	/* ==========================
	 * 		Public methods
	 * ==========================
	 */

	
	public static void tryLogin(String username, String password)
	{
		boolean status = MenuActionSender.tryLogin(username, password);
		if (status) 
		{
			Collection<String> gameNames = MenuActionSender.getJoinableGames();
			ScreenSwitcher.openLobbyScreen(gameNames);
		}
		//return status;
	}
	

	public static void tryCreateAccount(String username, String password)
	{
		boolean status = MenuActionSender.tryCreateAccount(username, password);
		if (status) 
		{
			Collection<String> gameNames = MenuActionSender.getJoinableGames();
			ScreenSwitcher.openLobbyScreen(gameNames);
		}
		//return status;
	}

	public static void gameSelected(String gameName)
	{
		boolean status = MenuActionSender.tryJoiningGame(gameName);
		if (status)
		{
			ScreenSwitcher.openGameRoomScreen(gameName);
		}
		else
		{
			Collection<String> gameNames = MenuActionSender.getJoinableGames();
			ScreenSwitcher.openLobbyScreen(gameNames);
		}
	}
	
	public static void tryCreateGame(String gameName, int numberPlayers)
	{
		boolean status = MenuActionSender.tryCreateGame(gameName, numberPlayers);
		if (status)
		{
			ScreenSwitcher.openGameRoomScreen(gameName);
		}
	}
	
	
	
	/* ==========================
	 * 		Private methods
	 * ==========================
	 */



	/* ========================
	 * 		Static methods
	 * ========================
	 */

}