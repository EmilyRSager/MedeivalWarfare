package mw.client.controller.menuing;

import java.util.Collection;

import mw.client.controller.menuing.ScreenSwitcher.ScreenKind;
import mw.shared.SharedCreatedGame;
import mw.shared.SharedGameLobby;

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
		SharedCreatedGame gameRoom = MenuActionSender.tryJoiningGame(gameName);
		if (gameRoom != null)
		{
			ScreenSwitcher.openGameRoomScreen(gameRoom);
		}
		else
		{
			openGameLobby();
		}
	}
	
	public static void tryCreateGame(String gameName, int numberPlayers)
	{
		SharedCreatedGame result = MenuActionSender.tryCreateGame(gameName, numberPlayers);
		if (result != null)
		{
			ScreenSwitcher.openGameRoomScreen(result);
		}
	}
	
	public static void inviteAccepted(String gameName)
	{
		SharedCreatedGame gameRoom = MenuActionSender.tryJoiningGame(gameName);
		if (gameRoom != null)
		{
			ScreenSwitcher.openGameRoomScreen(gameRoom);
		}
	}
	
	public static void leaveGameRoom()
	{
		MenuActionSender.leaveGameRoom();
		openGameLobby();
	}
	

	
	public static void openGameLobby()
	{
		SharedGameLobby joinableGames = MenuActionSender.getJoinableGames();
		ScreenSwitcher.openLobbyScreen(joinableGames);
	}
}