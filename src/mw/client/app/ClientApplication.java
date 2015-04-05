package mw.client.app;


import mw.client.app.test.SwingLoginWindow;
import mw.client.network.NetworkDriver;
import mw.client.network.ServerChannel;

public final class ClientApplication {

	public static void main(String[] args)
	{
		new ServerChannel();
		SwingLoginWindow.main(args);
		//NetworkDriver.main(args);
	}
	
}