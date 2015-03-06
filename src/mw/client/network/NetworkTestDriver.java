package mw.client.network;

import java.util.HashSet;
import java.util.Set;

import mw.client.gui.Hexagon;
import mw.shared.servercommands.NewGameRequestCommand;
import mw.shared.servercommands.TestNewGameCommand;

public class NetworkTestDriver {
	public static void main(String[] args) {
		ServerChannel s1 = new ServerChannel();
		
		Set<Integer> lRecipientIDs = new HashSet<Integer>();
		lRecipientIDs.add(0);
		
		
		s1.sendCommand(new NewGameRequestCommand());
	}
}
