package mw.server.app;

import mw.server.network.communication.MainServerThread;

public class MainApplication {
	public static void main(String[] args) {
		new MainServerThread().start();
	}
}
