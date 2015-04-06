package mw.client.controller.menuing;

import java.util.Collection;
import java.util.List;

import mw.client.gui.menuing.CreateAccountWindow;
import mw.client.gui.menuing.LoginWindow;

public abstract class ScreenSwitcher {


	public enum ScreenKind { NONE, LOGIN, CREATE_ACCOUNT, LOBBY, GAME_CREATION, GAME_ROOM };
	
	private static ScreenKind currentScreen = ScreenKind.NONE;
	
	private static LoginWindow loginScreen;
	private static CreateAccountWindow createAccountScreen;
	private static LobbyWindow lobbyScreen;
	private static GameRoomWindow gameRoomScreen;
	private static GameCreationWindow gameCreationScreen;

	/* ========================
	 * 		Constructors
	 * ========================
	 */



	/* ==========================
	 * 		Public methods
	 * ==========================
	 */
	
	
	public static void switchScreen(ScreenKind newScreen)
	{
		changeState(newScreen);
		switch (newScreen)
		{
		case NONE:
			break;
			
		case LOGIN:
			loginScreen = new LoginWindow();
			break;
			
		case CREATE_ACCOUNT:
			createAccountScreen = new CreateAccountWindow();
			break;
			
		default:
			throw new IllegalArgumentException("The ScreenKind "+newScreen+" needs parameters, you need to use another method for it");
			break;
		}
	}

	public static void openLobbyScreen(Collection<String> gameNames)
	{
		changeState(ScreenKind.LOBBY);
		lobbyScreen = new LobbyWindow((String[])gameNames.toArray());
	}

	public static void openGameRoomScreen(String gameName)
	{
		changeState(ScreenKind.GAME_ROOM);
		gameRoomScreen = new GameRoomWindow(gameName);
	}
	
	/* ==========================
	 * 		Private methods
	 * ==========================
	 */

	private static void changeState(ScreenKind newScreen)
	{
		//checkTransition(currentScreen, newScreen);
		closeCurrentWindow();
		currentScreen = newScreen;
	}
	
	private static void closeCurrentWindow()
	{
		switch (currentScreen)
		{
		case NONE:
			break;
			
		case LOGIN:
			loginScreen.close();
			loginScreen = null;
			break;
			
		case CREATE_ACCOUNT:
			createAccountScreen.close();
			createAccountScreen = null;
			break;
			
		case LOBBY:
			lobbyScreen.close();
			lobbyScreen = null;
			break;
			
		case GAME_CREATION:
			gameCreationScreen.close();
			gameCreationScreen = null;
			break;
			
		case GAME_ROOM:
			gameRoomScreen.close();
			gameRoomScreen = null;
			break;
		}
		
		currentScreen = ScreenKind.NONE;
	}

	/* ==========================
	 * 		Inherited methods
	 * ==========================
	 */

	/* ========================
	 * 		Static methods
	 * ========================
	 */
	
	/*private static void checkTransition(ScreenKind current, ScreenKind next)
	{
		boolean valid = true;
		switch (current)
		{
		case NONE:
			valid = (next == ScreenKind.LOGIN);
			break;
			
		case LOGIN:
			valid = (next == ScreenKind.CREATE_ACCOUNT || next == ScreenKind.LOBBY);
			break;
			
		case CREATE_ACCOUNT:
			valid = (next == ScreenKind.LOBBY);
			break;
			
		case LOBBY:
			valid = (next == ScreenKind.GAME_CREATION || next == ScreenKind.GAME_ROOM);
			break;
			
		case GAME_CREATION:
			valid = (next == ScreenKind.GAME_ROOM);
			break;
			
		case 
		}
		
		if (!valid)
			throw new IllegalStateException("Can't switch from "+current+" screen to "+next+" screen");
	}*/

}