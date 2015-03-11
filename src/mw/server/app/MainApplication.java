
package mw.server.app;

import mw.server.network.communication.MainServerThread;

/**
 * @author cbloom7
 * Provides access to initializing the server code.
 */
public class MainApplication {
	
	/**
	 * Main method.
	 * @param args
	 */
	public static void main(String[] args) {
		new MainServerThread().start();
	}
}
