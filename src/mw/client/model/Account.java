package mw.client.model;

//	UNUSED CLASS !
public final class Account {

	private static Account currentAccount = null;
	
	private String username;
	
	/**
	 * Creates a new Account, logged in by default, 
	 * with the name given
	 * @param name the username of the newly created Account
	 */
	public Account(String name)
	{
		username=name;
	}
	
	public static Account getCurrentAccount() { return currentAccount; }
	
	public static void logIn(Account a)
	{
		
	}
	
	public static void logOff()
	{
		if (currentAccount==null)
			throw new IllegalStateException("Cannot log off when no account is logged in.");
	}
}
