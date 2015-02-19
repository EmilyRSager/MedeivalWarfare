
package mw.server.network;
import mw.client.network.Client;

/**
 * @author Charlie Bloomfield
 * Feb 15, 2015
 */
public class NetworkMessageTestDriver {

	public static void main(String[] args) {
		
		Client c0 = new Client();
		c0.sendString("test");
		c0.readString();
		
	}
}
