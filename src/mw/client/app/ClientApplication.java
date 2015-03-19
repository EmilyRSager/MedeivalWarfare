package mw.client.app;


import java.util.ArrayList;

import org.minueto.MinuetoEventQueue;

import mw.client.controller.guimodel.ActionInterpreter;
import mw.client.controller.guimodel.ModelViewMapping;
import mw.client.controller.guimodel.TileModificationHandler;
import mw.client.controller.netmodel.ClientSynchronization;
import mw.client.controller.CurrentClientState;
import mw.client.gui.window.GameWindow;
import mw.client.gui.window.ImageTile;
import mw.client.gui.window.MapDisplay;
import mw.client.model.Game;
import mw.client.model.GameMap;
import mw.client.model.ModelTile;
import mw.client.network.NetworkDriver;

public final class ClientApplication {

	
	public static GameWindow window;
	public static Game game;
	private static boolean displaying = false;
	//private static ModelTile randomTile;
	
	
	public static void main(String[] args)
	{
		NetworkDriver.main(args);
	}
	
	
	public static void newGame(ModelTile[][] tiles)
	{
		final int width = tiles[0].length;
		final int height = tiles.length;
		
		TileModificationHandler observer = new TileModificationHandler();
		ArrayList<ModelTile> tileList = new ArrayList<ModelTile>();
		ImageTile displayedTiles[][] = new ImageTile[width][height];
		ModelViewMapping.initialize();
		ModelViewMapping mapping = ModelViewMapping.singleton();
		
		for (int i=0; i<width; i++)
		{
			for (int j=0; j<height; j++)
			{
				ModelTile t = tiles[j][i];
				tileList.add(t);
				t.addObserver(observer);
				
				ImageTile td = new ImageTile();
				displayedTiles[i][j] = td;
				
				mapping.addBinding(t, td);
				TileModificationHandler.displayTile(t, td);
			}
		}
		
		GameMap m = new GameMap(tileList);
		game = new Game(m, null);
		
		MapDisplay mapdisp = new MapDisplay(displayedTiles);
		window = new GameWindow(mapdisp);
		

		// Controllers setup
		
		CurrentClientState.setCurrentGame(game);
		ActionInterpreter.initialize(game);
		CurrentClientState.setCurrentGameWindow(window);
	}
	
	
	public static void startDisplay()
	{
		//window.setVisible(true);
		window.render();
		displaying = true;
	}
	
	
	public static void concurrentlyDisplay()
	{
		if (displaying)
			throw new IllegalStateException("Already displaying. A call to concurrentlyDisplay is illegal");
		
		Thread displayThread = new Thread() {
			public void run() {
				startDisplay();
				while(true)
				{
					MinuetoEventQueue queue = window.getEventQueue();
					while(queue.hasNext())
					{
						ClientSynchronization.gameLock.lock();
						queue.handle();
						ClientSynchronization.gameLock.unlock();
					}
				}
			}
		};
		displayThread.start();
	}
	
}