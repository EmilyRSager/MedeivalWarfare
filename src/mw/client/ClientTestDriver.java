package mw.client;

import mw.client.controller.menuing.MenuActionSender;
import mw.client.network.NetworkController;

public class ClientTestDriver {
	public static void main(String[] args) {
		NetworkController.initialize();
		
		int name = 0;
		if (name == 0)
			MenuActionSender.tryLogin("Charlie", "Bloomfield");
		else
			MenuActionSender.tryLogin("Hugo", "Kapp");
		
		NetworkController.requestNewGame();
	}
}
