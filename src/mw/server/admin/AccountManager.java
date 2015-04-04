package mw.server.admin;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;
import java.util.UUID;

import com.google.gson.Gson;

import mw.util.Cache;
import mw.util.CacheValueComputer;
import mw.filesystem.ProjectFolder;

public class AccountManager {
	private static final String ACCOUNTS_FOLDER_PATH = "accountdata/accounts/";
	private static AccountManager aAccountManager;
	private HashMap<UUID, Account> aAccountCache;
	
	/**
	 * Constructor
	 */
	private AccountManager(){
		aAccountCache = new HashMap<UUID, Account>();
	}
	
	/**
	 * @return static singleton instance
	 */
	public static AccountManager getInstance(){
		if(aAccountManager == null){
			aAccountManager = new AccountManager();
		}
		
		return aAccountManager;
	}
	
	/**
	 * Creates a new account 
	 * @param pUsername
	 * @param pPassword
	 * @return 
	 * @throws Exception
	 */
	public Account createAccount(String pUsername, String pPassword) throws Exception{
		//TODO verify account doesn't exist already
		UUID lUUID = UUID.randomUUID();
		Account lNewAccount = new Account(lUUID, pUsername, pPassword);
		
		saveAccountData(lNewAccount);
		
		
		aAccountCache.put(lUUID, lNewAccount);
		return lNewAccount;
	}
	
	/**
	 * @param pUUID
	 * @return
	 */
	public Account getAccount(UUID pUUID){
		if(aAccountCache.containsKey(pUUID)){
			return aAccountCache.get(pUUID);
		}
		else{
			Account lAccount = loadAccount(pUUID);
			aAccountCache.put(pUUID, lAccount);
			return lAccount;
		}
	}
	
	/**
	 * @param pUUID
	 * @return
	 */
	private Account loadAccount(UUID pUUID){
		Scanner lScanner = new Scanner(getAccountsFilePath(pUUID));
		
		String lSerializedAccount = null;
		while(lScanner.hasNextLine()){
			lSerializedAccount += lScanner.nextLine();
		}
		
		Account lAccount = new Gson().fromJson(lSerializedAccount, Account.class);
		
		lScanner.close();
		return lAccount;
	}

	/**
	 * @param pAccount
	 */
	public void saveAccountData(Account pAccount){
		FileWriter lFileWriter = null;
		try {
			String lAccountFilePath = getAccountsFilePath(pAccount.getID());
			System.out.println(lAccountFilePath);
			lFileWriter = new FileWriter(lAccountFilePath);
		} catch (IOException e){
			System.out.println("[AccountSerializer] Failed to load the account data file.");
			e.printStackTrace();
		}

		try {
			
			lFileWriter.write(pAccount.toString());
		} catch (IOException e) {
			System.out.println("[AccountSerializer] Failed to write account data to the account data file.");
			e.printStackTrace();
		}

		if(lFileWriter != null){
			try {
				lFileWriter.close();
			} catch (IOException e) {
				System.out.println("[AccountSerializer] Failed to close file writer.");
				e.printStackTrace();
			}
		}
	}

	private static String getAccountsFilePath(UUID pAccountID){
		return ProjectFolder.getPath() + ACCOUNTS_FOLDER_PATH + pAccountID;
	}

	public static void main(String[] args) {
		Account a;
		try {
			a = AccountManager.getInstance().createAccount("Charlie", "Hugo");
			a.incrementLosses();
			a.incrementWins();
			AccountManager.getInstance().saveAccountData(a);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
