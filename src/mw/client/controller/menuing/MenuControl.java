package mw.client.controller.menuing;

import mw.client.controller.menuing.ScreenSwitcher.ScreenKind;

public abstract class MenuControl {

	/* ========================
	 * 		Constructors
	 * ========================
	 */



	/* ==========================
	 * 		Public methods
	 * ==========================
	 */

	public static boolean tryLogin(String username, String password)
	{
		boolean status = MenuActionSender.tryLogin(username, password);
		if (status) {
			ScreenSwitcher.switchScreen(ScreenKind.LOBBY);
		}
		return status;
	}
	

	public static boolean tryCreateAccount(String username, String password)
	{
		boolean status = MenuActionSender.tryCreateAccount(username, password);
		if (status) {
			ScreenSwitcher.switchScreen(ScreenKind.LOBBY);
		}
		return status;
	}

	/* ==========================
	 * 		Private methods
	 * ==========================
	 */


	/* ==========================
	 * 		Inherited methods
	 * ==========================
	 */



	/* ========================
	 * 		Static methods
	 * ========================
	 */

}