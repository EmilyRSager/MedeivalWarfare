package mw.client.app;

import java.util.ArrayList;
import java.util.Random;

import mw.client.controller.CurrentClientState;
import mw.client.controller.ModelViewMapping;
import mw.client.controller.NewStateApplier;
import mw.client.controller.TileModificationHandler;
import mw.client.gui.GameWindow;
import mw.client.gui.ImageTile;
import mw.client.gui.MapDisplay;
import mw.client.model.Game;
import mw.client.model.GameMap;
import mw.client.model.Player;
import mw.client.model.ModelTile;
import mw.shared.SharedColor;
import mw.shared.SharedCoordinates;
import mw.shared.SharedTile;

public final class MainApplication {

	private static final Player PLAYER = null;
	public final static int DEFAULT_MAP_WIDTH = 18;
	public final static int DEFAULT_MAP_HEIGHT = 18;

	
	private static GameWindow window;
	private static Game game;
	//private static ModelTile randomTile;
	
	public static void main(String[] args)
	{
		newGame();
		startDisplay();

		waitABit();
		testUpdate(SharedColor.BLUE);
		waitABit();
		testUpdate(SharedColor.YELLOW);
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
		SharedTile newST = new SharedTile(c, new SharedCoordinates(r.nextInt(DEFAULT_MAP_WIDTH), r.nextInt(DEFAULT_MAP_HEIGHT)), SharedTile.Terrain.GRASS, false);
		NewStateApplier.applyChanges(game, newST);
	}
	
	public static void newGame()
	{
		ModelTile[][] newTiles = new ModelTile[DEFAULT_MAP_WIDTH][DEFAULT_MAP_HEIGHT];
		for (int i=0; i<DEFAULT_MAP_WIDTH; i++)
		{
			for (int j=0; j<DEFAULT_MAP_HEIGHT; j++)
			{
				ModelTile t = new ModelTile(i, j);
				t.setColor(SharedColor.GREEN);
				newTiles[i][j] = t;
			}
		}
		newGame(newTiles);
	}
	
	public static void newGame(ModelTile[][] tiles)
	{
		TileModificationHandler observer = new TileModificationHandler();
		ArrayList<ModelTile> tileList = new ArrayList<ModelTile>();
		ImageTile displayedTiles[][] = new ImageTile[DEFAULT_MAP_WIDTH][DEFAULT_MAP_HEIGHT];
		ModelViewMapping.initialize();
		ModelViewMapping mapping = ModelViewMapping.singleton();
		
		final int width = tiles.length;
		final int height = tiles[0].length;
		for (int i=0; i<width; i++)
		{
			for (int j=0; j<height; j++)
			{
				ModelTile t = tiles[i][j];
				tileList.add(t);
				t.addObserver(observer);
				/*if (i==2 && j==1)
					randomTile = t;*/
				
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
		
		//ActionInterpreter.initialize(game);
		CurrentClientState.setCurrentGame(game);
	}
	
	public static void startDisplay()
	{
		window.render();
	}
	
}