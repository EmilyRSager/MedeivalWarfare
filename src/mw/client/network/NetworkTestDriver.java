package mw.client.network;

import mw.shared.servercommands.NewGameRequestCommand;

public class NetworkTestDriver {
	public static void main(String[] args) {
		ServerChannel s1 = new ServerChannel();
		
		s1.sendCommand(new NewGameRequestCommand());
	}
}
