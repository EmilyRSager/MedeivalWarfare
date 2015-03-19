package mw.client.controller.guimodel;

import java.util.ArrayList;

import org.minueto.MinuetoEventQueue;

import mw.client.controller.CurrentClientState;
import mw.client.controller.netmodel.ClientSynchronization;
import mw.client.gui.window.GameWindow;
import mw.client.gui.window.ImageTile;
import mw.client.gui.window.MapDisplay;
import mw.client.model.Game;
import mw.client.model.GameMap;
import mw.client.model.ModelTile;

public final class GameInitializer {

	public static void newGame(ModelTile[][] tiles)
	{
		Game game;
		GameWindow window;
		
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
		CurrentClientState.setCurrentGameWindow(window);
		
		initializeControllers();
		
		startDisplay();
	}

	public static void initializeControllers()
	{
		ActionInterpreter.initialize(CurrentClientState.getCurrentGame());
	}

	public static void startDisplay()
	{
		//if (displaying)
			//throw new IllegalStateException("Already displaying. A call to concurrentlyDisplay is illegal");
		
		Thread displayThread = new Thread() {
			public void run() {
				
				while(true)
				{
					MinuetoEventQueue queue = CurrentClientState.getCurrentGameWindow().getEventQueue();
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