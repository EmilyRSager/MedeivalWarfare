package mw.client.network;

<<<<<<< HEAD
import mw.shared.servercommands.NewGameRequestCommand;
=======
import java.util.HashSet;
import java.util.Set;

import mw.client.gui.Hexagon;
import mw.shared.servercommands.NewGameRequestCommand;
import mw.shared.servercommands.TestNewGameCommand;
>>>>>>> 6a4e1442138cec220db35bfa222923bee16f84d0

public class NetworkTestDriver {
	public static void main(String[] args) {
		ServerChannel s1 = new ServerChannel();
		
<<<<<<< HEAD
=======
		Set<Integer> lRecipientIDs = new HashSet<Integer>();
		lRecipientIDs.add(0);
		
		
>>>>>>> 6a4e1442138cec220db35bfa222923bee16f84d0
		s1.sendCommand(new NewGameRequestCommand());
	}
}
