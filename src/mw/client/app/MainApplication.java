package mw.client.app;

import java.util.ArrayList;

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
import mw.client.model.Tile;
import mw.shared.SharedColor;
import mw.shared.SharedCoordinates;
import mw.shared.SharedTile;

public final class MainApplication {

	private static final Player PLAYER = null;
	private final static int MAP_WIDTH = 10;
	private final static int MAP_HEIGHT = 10;
	
	private static GameWindow window;
	private static Game game;
	private static Tile randomTile;
	
	public static void main(String[] args)
	{
		newGame();
		//window.render();
		startDisplay();
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		SharedTile newST = new SharedTile(SharedColor.BLUE, new SharedCoordinates(1, 2), SharedTile.Terrain.GRASS, false);
		NewStateApplier.applyChanges(game, newST);
		
		/*randomTile.setColor(SharedColor.BLUE);
		randomTile.notifyObservers();*/
		//window.update();
	}
	
	public static void newGame()
	{
		Tile[][] newTiles = new Tile[MAP_WIDTH][MAP_HEIGHT];
		for (int i=0; i<MAP_WIDTH; i++)
		{
			for (int j=0; j<MAP_HEIGHT; j++)
			{
				Tile t = new Tile(i, j);
				t.setColor(SharedColor.YELLOW);
				newTiles[i][j] = t;
			}
		}
		newGame(newTiles);
	}
	
	public static void newGame(Tile[][] tiles)
	{
		TileModificationHandler observer = new TileModificationHandler();
		ArrayList<Tile> tileList = new ArrayList<Tile>();
		ImageTile displayedTiles[][] = new ImageTile[MAP_WIDTH][MAP_HEIGHT];
		ModelViewMapping.initialize();
		ModelViewMapping mapping = ModelViewMapping.singleton();
		
		final int width = tiles.length;
		final int height = tiles[0].length;
		for (int i=0; i<width; i++)
		{
			for (int j=0; j<height; j++)
			{
				Tile t = tiles[i][j];
				tileList.add(t);
				t.addObserver(observer);
				if (i==2 && j==1)
					randomTile = t;
				
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