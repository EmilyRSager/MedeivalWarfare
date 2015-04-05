package mw.client;

import mw.client.controller.menuing.MenuActionSender;
import mw.client.network.NetworkController;

public class ClientTestDriver {
	public static void main(String[] args) {
		NetworkController.initialize();
		MenuActionSender.tryLogin("Charlie", "Bloomfield");
		NetworkController.requestNewGame();
	}
}
