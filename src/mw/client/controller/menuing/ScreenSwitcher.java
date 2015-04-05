package mw.client.controller.menuing;

public abstract class ScreenSwitcher {


	public enum ScreenKind { NONE, LOGIN, CREATE_ACCOUNT };
	
	private static ScreenKind currentScreen = ScreenKind.NONE;
	
	private static LoginWindow loginScreen;
	private static CreateAccountWindow createAccountScreen;

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
			valid = (next == ScreenKind.CREATE_ACCOUNT);
			break;
			
		case CREATE_ACCOUNT:
			valid=false;
			break;
		}
		
		if (!valid)
			throw new IllegalStateException("Can't switch from "+current+" screen to "+next+" screen");
	}

}