package mw.client.network;

import java.util.HashSet;
import java.util.Set;

import mw.shared.servercommands.NewGameRequestCommand;

public class NetworkDriver {
	public static void main(String[] args) {
		NetworkController.initializeServerChannel(new ServerChannel());
		NetworkController.requestNewGame();
	}
}
