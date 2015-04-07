package mw.server.admin;

import java.io.File;
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
	private static final String ACCOUNTS_FOLDER_PATH = "data/accountdata/accounts/";
	private static final String PASSWORDS_FILE_PATH = "data/accountdata/passwords";

	private static int USERNAME_INDEX = 0;
	private static int PASSWORD_INDEX = 1;
	private static int UUID_INDEX = 2;

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
	public UUID createAccount(String pUsername, String pPassword) throws IllegalArgumentException{
		if(!areValidCrentials(pUsername, pPassword)){
			throw new IllegalArgumentException("Invalid account credentials. Try a different username.");
		}
		UUID lUUID = UUID.randomUUID();
		//null param temporary for the gameaccount info
		Account lNewAccount = new Account(lUUID, pUsername, pPassword, null);

		//save to disk for persistence
		saveAccountData(lNewAccount);
		savePassword(lNewAccount);

		aAccountCache.put(lUUID, lNewAccount);
		return lUUID;
	}

	/**
	 * @param pUsername
	 * @param pPassword
	 * @return true if the parameter credentials are valid. False if the username already exists
	 */
	private boolean areValidCrentials(String pUsername, String pPassword){
		Scanner lScanner = null;
		try {
			lScanner = new Scanner(new File(getPasswordsFilePath()));
			String lCredentials, lExistingUsername;
			String[] lCredentialsFields;
			while(lScanner.hasNextLine()){
				lCredentials = lScanner.nextLine();
				lCredentialsFields = lCredentials.split(",");

				lExistingUsername = lCredentialsFields[USERNAME_INDEX];
				if(pUsername.equals(lExistingUsername)){
					lScanner.close();
					return false;
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return true;
	}

	/**
	 * @param pAccount
	 */
	private void savePassword(Account pAccount){
		FileWriter lFileWriter = null;
		try {
			lFileWriter = new FileWriter(getPasswordsFilePath(), true);
			lFileWriter.write(pAccount.getUsername() + "," + pAccount.getPassword() + "," + pAccount.getID().toString() + "\n");
			lFileWriter.close();
		} catch (IOException e) {
			System.out.println("[AccountManager] Failed to write username/password info.");
			e.printStackTrace();
		}
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
	public UUID getAccountID(String pUsername, String pPassword) throws IllegalArgumentException {
		Scanner lScanner = null;
		try {
			lScanner = new Scanner(new File(getPasswordsFilePath()));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String lAccountInfo = null;
		String[] lAccountFields;
		String lUsername, lPassword;
		while(lScanner.hasNextLine()){			
			lAccountInfo = lScanner.nextLine();
			lAccountFields = lAccountInfo.split(",");
			lUsername = lAccountFields[USERNAME_INDEX];
			lPassword = lAccountFields[PASSWORD_INDEX];

			if(pUsername.equals(lUsername) && pPassword.equals(lPassword)){
				lScanner.close();
				
				UUID lUUID = UUID.fromString(lAccountFields[UUID_INDEX]);
				System.out.println("U = " + lUsername + ". P = " + lPassword + ". ID = " + lUUID);
				
				return lUUID;
			}
		}

		lScanner.close();
		throw new IllegalArgumentException("Invalid login credentials.");
	}

	/**
	 * @param pAccount
	 */
	public void saveAccountData(Account pAccount){
		FileWriter lFileWriter = null;
		try {
			String lAccountFilePath = getAccountsFilePath(pAccount.getID());
			lFileWriter = new FileWriter(lAccountFilePath);
			lFileWriter.write(pAccount.toString());
			lFileWriter.close();
		} catch (IOException e) {
			System.out.println("[AccountManager] Failed to close file writer.");
			e.printStackTrace();
		}
	}

	/**
	 * @param pUUID
	 * @return the account with the parameter ID
	 */
	private Account loadAccount(UUID pUUID){
		Scanner lScanner = null;
		Account lAccount = null;
		
		try {
			lScanner = new Scanner(new File(getAccountsFilePath(pUUID)));
			String lSerializedAccount = "";
			while(lScanner.hasNextLine()){
				lSerializedAccount += lScanner.nextLine();
			}
			
			lAccount = new Gson().fromJson(lSerializedAccount, Account.class);
			
			lScanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		return lAccount;
	}

	private static String getPasswordsFilePath(){
		return ProjectFolder.getPath() + PASSWORDS_FILE_PATH;
	}

	private static String getAccountsFilePath(UUID pAccountID){
		return ProjectFolder.getPath() + ACCOUNTS_FOLDER_PATH + pAccountID;
	}
}
