package mw.client.controller.menuing;

import mw.client.network.NetworkController;
import mw.util.BlockingFuture;

public final class MenuActionSender {


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

	private static BlockingFuture<Boolean> authenticationResult;
	
	public static boolean tryLogin(String username, String password)
	{
		authenticationResult = new BlockingFuture<Boolean>();
		NetworkController.authenticateUser(username, password);
		return authenticationResult.getValue();
	}
	
	public static void setAuthenticationResult(boolean result) 
	{
		authenticationResult.setValue(result);
	}
	
	
	private static BlockingFuture<Boolean> accountCreationResult;
	
	public static boolean tryCreateAccount(String username, String password)
	{
		accountCreationResult = new BlockingFuture<Boolean>();
		NetworkController.createUserAccount(username, password);
		return accountCreationResult.getValue();
	}
	
	public static void setAccountCreationResult(boolean result)
	{
		accountCreationResult.setValue(result);
	}
	
}