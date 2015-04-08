package test.mw.server.gamelogic;

import mw.client.controller.menuing.MenuActionSender;
import mw.client.network.NetworkController;

public class TestSaveLoadGame {

	public static void main(String[] args) {
		NetworkController.initialize();
		
		int name = 1;
		if (name == 0){
			MenuActionSender.tryLogin("Charlie", "Bloomfield");
			NetworkController.loadGame("TestGame");
		}
		else{
			MenuActionSender.tryLogin("Hugo", "Kapp");
			NetworkController.joinGame("TestGame");
		}

	}
	
	
	
}
