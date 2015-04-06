package mw.client.controller.menuing;

import java.util.Collection;

import mw.client.controller.menuing.ScreenSwitcher.ScreenKind;

public abstract class MenuControl {


	
	public static void tryLogin(String username, String password)
	{
		boolean status = MenuActionSender.tryLogin(username, password);
		if (status) 
		{
			openGameLobby();
		}
	}
	

	public static void tryCreateAccount(String username, String password)
	{
		boolean status = MenuActionSender.tryCreateAccount(username, password);
		if (status) 
		{
			openGameLobby();
		}
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
			openGameLobby();
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
	
	
	
	public static void leaveGameRoom()
	{
		MenuActionSender.leaveGameRoom();
		openGameLobby();
	}
	

	
	public static void openGameLobby()
	{
		GameLobby joinableGames = MenuActionSender.getJoinableGames();
		ScreenSwitcher.openLobbyScreen(joinableGames);
	}
}