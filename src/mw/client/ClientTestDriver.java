package mw.client;

import mw.client.controller.menuing.MenuActionSender;
import mw.client.network.NetworkController;

public class ClientTestDriver {
	public static void main(String[] args) {
		NetworkController.initialize();
		//createAccountAndGame();
		//loginAndLoad();
		loginAndCreate();	
	}
	
	public static void loginAndCreate(){
		int name = 0;
		if (name == 0){
			MenuActionSender.tryLogin("Charlie", "Bloomfield");
			NetworkController.requestNewGame("TestGame3", 2);
		}
		else{
			MenuActionSender.tryLogin("Hugo", "Kapp");
			NetworkController.joinGame("TestGame3");
		}
	}
	
	public static void createAccountAndGame(){
		int name = 0;
		if (name == 0){
			MenuActionSender.tryCreateAccount("Charlie", "Bloomfield");
			NetworkController.requestNewGame("Test game", 2);
		}
		else{
			MenuActionSender.tryCreateAccount("Hugo", "Kapp");
			NetworkController.joinGame("Test game");
		}
	}
	
	public static void loginAndLoad(){
		int name = 0;
		if (name == 0){
			MenuActionSender.tryLogin("Charlie", "Bloomfield");
			NetworkController.loadGame("Test game");
		}
		else{
			MenuActionSender.tryLogin("Hugo", "Kapp");
			NetworkController.joinGame("Test game");
		}
	}
}
