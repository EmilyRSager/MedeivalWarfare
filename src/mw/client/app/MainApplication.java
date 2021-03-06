package mw.client.app;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.minueto.MinuetoEventQueue;

import mw.client.controller.guimodel.*;
import mw.client.controller.guimodel.ChoiceCenter.ChoiceType;
import mw.client.controller.CurrentClientState;
import mw.client.controller.model.*;
import mw.client.gui.window.*;
import mw.client.model.Game;
import mw.client.model.GameMap;
import mw.client.model.Player;
import mw.client.model.ModelTile;
import mw.shared.SharedColor;
import mw.shared.Coordinates;
import mw.shared.SharedTile;

public final class MainApplication {

	private static final Player PLAYER = new Player(SharedColor.YELLOW, "player");
	
	public final static int DEFAULT_MAP_WIDTH = 18;
	public final static int DEFAULT_MAP_HEIGHT = 18;
	
	
	private static GameWindow window;
	private static Game game;
	private static boolean displaying = false;
	//private static ModelTile randomTile;
	
	public static void main(String[] args)
	{
		newGame();
		startDisplay();
		
		waitABit();
		testUpdate(SharedColor.BLUE);
		waitABit();
		testUpdate(SharedColor.YELLOW);
		waitABit();
		DisplayUpdater.showVillageResources(200, 200);
		List<String> crap = new ArrayList<String>();
		crap.add("crap1");
		crap.add("crap2");
		crap.add("crap3 is actually long");
		DisplayUpdater.displayChoice(ChoiceType.UNIT_ACTION, crap);
		DisplayUpdater.showEndTurnButton(true);
		waitABit();
		
		while(true)
		{
			MinuetoEventQueue queue = window.getEventQueue();
			while(queue.hasNext())
			{
				queue.handle();
			}
		}
	}
	
	public static void waitABit()
	{
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void testUpdate(SharedColor c)
	{
		Random r = new Random();
		SharedTile newST = new SharedTile(c,
					new Coordinates(r.nextInt(DEFAULT_MAP_HEIGHT), r.nextInt(DEFAULT_MAP_WIDTH)),
					SharedTile.Terrain.SEA, false,
					SharedTile.UnitType.NONE,
					SharedTile.VillageType.NONE, 0, 0);
		NewStateApplier.applyChanges(game, newST);
	}
	
	public static void newGame()
	{
		ModelTile[][] newTiles = new ModelTile[DEFAULT_MAP_WIDTH][DEFAULT_MAP_HEIGHT];
		for (int i=0; i<DEFAULT_MAP_WIDTH; i++)
		{
			for (int j=0; j<DEFAULT_MAP_HEIGHT; j++)
			{
				ModelTile t = new ModelTile(j, i);
				t.setColor(SharedColor.GREEN);
				newTiles[i][j] = t;
			}
		}
		newGame(newTiles);
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
		game = new Game(m, PLAYER);
		
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
						queue.handle();
					}
				}
			}
		};
		displayThread.start();
	}
	
}