package mw.client.controller.menuing;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import mw.client.gui.menuing.CreateAccountWindow;
import mw.client.gui.menuing.CreateGameWindow;
import mw.client.gui.menuing.GameLobbyWindow;
import mw.client.gui.menuing.GameRoomWindow;
import mw.client.gui.menuing.InvitationWindow;
import mw.client.gui.menuing.LoginWindow;
import mw.shared.SharedCreatedGame;
import mw.shared.SharedGameLobby;

public abstract class ScreenSwitcher {


	public enum ScreenKind { NONE, LOGIN, CREATE_ACCOUNT, LOBBY, GAME_CREATION, GAME_ROOM };
	
	private static ScreenKind currentScreen = ScreenKind.NONE;
	
	private static LoginWindow loginScreen;
	private static CreateAccountWindow createAccountScreen;
	private static GameLobbyWindow lobbyScreen;
	private static GameRoomWindow gameRoomScreen;
	private static CreateGameWindow gameCreationScreen;
	
	private static List<InvitationWindow> openedInviteWindows = new ArrayList<InvitationWindow>();

	/* ========================
	 * 		Constructors
	 * ========================
	 */



	/* ==========================
	 * 		Public methods
	 * ==========================
	 */
	
	
	public static synchronized void switchScreen(ScreenKind newScreen)
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
			
		case GAME_CREATION:
			gameCreationScreen = new CreateGameWindow();
			break;
			
		default:
			throw new IllegalArgumentException("The ScreenKind "+newScreen+" needs parameters, you need to use another method for it");
		}
	}

	public static synchronized void openLobbyScreen(SharedGameLobby lobby)
	{
		changeState(ScreenKind.LOBBY);
		
		lobbyScreen = new GameLobbyWindow(lobby);
	}

	public static synchronized void openGameRoomScreen(SharedCreatedGame game)
	{
		changeState(ScreenKind.GAME_ROOM);
		gameRoomScreen = new GameRoomWindow(game);
	}
	
	public static synchronized void openInvite(SharedCreatedGame game)
	{
		if (currentScreen == ScreenKind.LOBBY)
		{
			openedInviteWindows.add(new InvitationWindow(game));
		}
	}
	
	/* ==========================
	 * 		Private methods
	 * ==========================
	 */

	private static synchronized void changeState(ScreenKind newScreen)
	{
		//checkTransition(currentScreen, newScreen);
		closeCurrentWindow();
		currentScreen = newScreen;
	}
	
	private static synchronized void closeCurrentWindow()
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
			for (int i=0; i < openedInviteWindows.size(); i++) {
				openedInviteWindows.get(i).close();
				openedInviteWindows.remove(i);
			}
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