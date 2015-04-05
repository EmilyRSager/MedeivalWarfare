package mw.server.admin;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;
import java.util.UUID;

import com.google.gson.Gson;
import com.sun.org.apache.xml.internal.security.signature.InvalidDigestValueException;

import mw.shared.servercommands.CreateAccountCommand;
import mw.util.Cache;
import mw.util.CacheValueComputer;
import mw.filesystem.ProjectFolder;

/**
 * @author cbloom7
 * Awful class that sucks a lot.
 */
public class AccountManager {
	private static final String ACCOUNTS_FOLDER_PATH = "accountdata/accounts/";
	private static final String PASSWORDS_FILE_PATH = "accountdata/passwords.csv";
	
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
	public UUID createAccount(String pUsername, String pPassword) throws Exception{
		if(!areValidCrentials(pUsername, pPassword)){
			throw new IllegalArgumentException("Invalid account credentials. Try a different username.");
		}
		UUID lUUID = UUID.randomUUID();
		Account lNewAccount = new Account(lUUID, pUsername, pPassword);
		
		//save to disk for persistence
		saveAccountData(lNewAccount);
		savePassword(lNewAccount);
		
		aAccountCache.put(lUUID, lNewAccount);
		return lUUID;
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
	 * @param pUsername
	 * @param pPassword
	 * @return Account id associated with the parameter username and password
	 * @throws Exception
	 */
	public UUID getAccountID(String pUsername, String pPassword) {
		Scanner lScanner = new Scanner(getPasswordsFilePath());
		
		String lAccountInfo = null;
		String[] lAccountFields;
		String lUsername, lPassword;
		while(lScanner.hasNextLine()){
			lAccountInfo = lScanner.nextLine();
			lAccountFields = lAccountInfo.split(",");
			lUsername = lAccountFields[0];
			lPassword = lAccountFields[1];
			
			if(pUsername.equals(lUsername) && pPassword.equals(lPassword)){
				return UUID.fromString(lAccountFields[2]);
			}
		}
		
		throw new IllegalArgumentException("Invalid login credentials.");
	}

	/**
	 * @param pUUID
	 * @return the account with the parameter ID
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
	 * @param pUsername
	 * @param pPassword
	 * @return true if the parameter credentials are valid. False if the username already exists
	 */
	private boolean areValidCrentials(String pUsername, String pPassword){
		Scanner lScanner = new Scanner(getPasswordsFilePath());
		String lCredentials, lTmpUsername;
		String[] lCredentialsFields;
		
		
		while(lScanner.hasNextLine()){
			lCredentials = lScanner.nextLine();
			lCredentialsFields = lCredentials.split(",");
			lTmpUsername = lCredentialsFields[0];
			if(pUsername.equals(lTmpUsername)){
				lScanner.close();
				return false;
			}
		}
		
		lScanner.close();
		return true;
	}
	
	/**
	 * @param pAccount
	 */
	private void savePassword(Account pAccount){
		FileWriter lFileWriter = null;
		try {
			lFileWriter = new FileWriter(getPasswordsFilePath(), true);
		} catch (IOException e) {
			System.out.println("[AccountManager] Failed to open username/password file.");
			e.printStackTrace();
		}
		
		try {
			lFileWriter.write(pAccount.getUsername() + "," + pAccount.getPassword() + "," + pAccount.getID().toString() + "\n");
		} catch (IOException e) {
			System.out.println("[AccountManager] Failed to save account username/password to file");
			e.printStackTrace();
		}
	}

	/**
	 * @param pAccount
	 */
	public void saveAccountData(Account pAccount){
		FileWriter lFileWriter = null;
		try {
			String lAccountFilePath = getAccountsFilePath(pAccount.getID());
			//System.out.println(lAccountFilePath);
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
	
	private static String getPasswordsFilePath(){
		return ProjectFolder.getPath() + PASSWORDS_FILE_PATH;
	}

	private static String getAccountsFilePath(UUID pAccountID){
		return ProjectFolder.getPath() + ACCOUNTS_FOLDER_PATH + pAccountID;
	}
}
