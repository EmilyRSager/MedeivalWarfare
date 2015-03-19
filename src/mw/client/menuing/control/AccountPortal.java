package mw.client.menuing.control;

import mw.client.network.NetworkController;

public final class AccountPortal {


	/* ========================
	 * 		Constructors
	 * ========================
	 */



	/* ==========================
	 * 		Public methods
	 * ==========================
	 */


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

	public static void tryLogin(String username, String password)
	{
		NetworkController.authenticateUser(username, password);
	}
	
}