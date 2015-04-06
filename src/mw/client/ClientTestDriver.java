package mw.client;

import mw.client.controller.menuing.MenuActionSender;
import mw.client.network.NetworkController;

public class ClientTestDriver {
	public static void main(String[] args) {
		NetworkController.initialize();
		int x = 1;
		if (x == 0)
		{
			MenuActionSender.tryLogin("Charlie", "Bloomfield");
		}
		if (x != 0)
		{
			MenuActionSender.tryLogin("Hugo", "Kapp");
		}
		NetworkController.requestNewGame();
	}
}
