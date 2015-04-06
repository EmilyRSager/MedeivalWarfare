package mw.client.controller.menuing;

import mw.client.gui.menuing.CreateAccountWindow;
import mw.client.gui.menuing.LoginWindow;

public abstract class ScreenSwitcher {


	public enum ScreenKind { NONE, LOGIN, CREATE_ACCOUNT, LOBBY };
	
	private static ScreenKind currentScreen = ScreenKind.NONE;
	
	private static LoginWindow loginScreen;
	private static CreateAccountWindow createAccountScreen;
	//private static LobbyWindow lobbyScreen;

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
		checkTransition(currentScreen, newScreen);
		closeCurrentWindow();
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
			
		case LOBBY:
			//lobbyScreen = new LobbyWindow();
			break;
		}
		currentScreen = newScreen;
	}


	/* ==========================
	 * 		Private methods
	 * ==========================
	 */

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
			//lobbyScreen.close();
			//lobbyScreen = null;
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
	
	private static void checkTransition(ScreenKind current, ScreenKind next)
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
		}
		
		if (!valid)
			throw new IllegalStateException("Can't switch from "+current+" screen to "+next+" screen");
	}

}