package mw.client.app;


import mw.client.controller.menuing.ScreenSwitcher;
import mw.client.controller.menuing.ScreenSwitcher.ScreenKind;
import mw.client.network.NetworkController;
import mw.client.network.NetworkDriver;

public final class ClientApplication {

	public static void main(String[] args)
	{
		NetworkController.initialize();
		ScreenSwitcher.switchScreen(ScreenKind.LOGIN);
		//NetworkDriver.main(args);
	}
	
}