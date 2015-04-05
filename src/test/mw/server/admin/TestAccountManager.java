package test.mw.server.admin;

import java.util.UUID;

import mw.server.admin.Account;
import mw.server.admin.AccountManager;

public class TestAccountManager {
	public static void main(String[] args) {
		UUID id;
		try {
			id = AccountManager.getInstance().createAccount("Charlie", "Hudo");
			Account a = AccountManager.getInstance().getAccount(id);
			a.incrementLosses();
			a.incrementWins();
			AccountManager.getInstance().saveAccountData(a);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			UUID id2 = AccountManager.getInstance().createAccount("Charlie", "Hudo");
		} catch (Exception e) {
			System.out.println("[TestAccountManager] Error creating account with existing credentials.");
		}
	}
}
