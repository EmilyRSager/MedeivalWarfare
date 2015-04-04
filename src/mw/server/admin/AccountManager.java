package mw.server.admin;

import java.io.FileWriter;
import java.io.IOException;

import mw.filesystem.ProjectFolder;

public class AccountManager {
	private static final String ACCOUNT_DATA_FILE_NAME = "accountdata.txt";

	public static void saveAccount(Account pAccount){
		FileWriter lFileWriter = null;
		try {
			lFileWriter = new FileWriter(getAccountDataFilePath());
		} catch (IOException e) {
			System.out.println("[AccountSerializer] Failed to load the account data file.");
			e.printStackTrace();
		}
		
		String lSerializedAccount = pAccount.toString();
		System.out.println("[AccountSerializer] Writing following account info to data file:");
		System.out.println(lSerializedAccount);

		try {
			lFileWriter.append(pAccount.toString());
		} catch (IOException e) {
			System.out.println("[AccountSerializer] Failed to write account data to the account data file.");
			e.printStackTrace();
		}

		if(lFileWriter != null){
			try {
				lFileWriter.close();
			} catch (IOException e) {
				System.out.println("[AccountSerializer] Failed to ");
				e.printStackTrace();
			}
		}
	}

	public static Account loadAccount(int pAccountID){
		Account lAccount = null;

		return lAccount;
	}

	private static String getAccountDataFilePath(){
		return ProjectFolder.getPath() + ACCOUNT_DATA_FILE_NAME;
	}

	public static void main(String[] args) {
		Account a = new Account(1, "Charlie", "Hugo");
		saveAccount(a);
	}
}
