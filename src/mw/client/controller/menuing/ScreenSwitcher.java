package mw.client.controller.menuing;

public abstract class ScreenSwitcher {


	public enum ScreenKind { NONE, LOGIN };
	
	private static final ScreenKind currentScreen = ScreenKind.NONE;
	
	private static LoginScreen loginScreen;

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
		closeCurrentWindow();
		switch (newScreen)
		{
		case NONE:
			break;
			
		case LOGIN:
			loginScreen = new LoginScreen();
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
	
	

}