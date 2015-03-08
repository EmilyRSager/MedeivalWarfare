package mw.client.network;

public class NetworkDriver {
	public static void main(String[] args) {
		NetworkController.initializeServerChannel(new ServerChannel());
		NetworkController.requestNewGame();
	}
}
