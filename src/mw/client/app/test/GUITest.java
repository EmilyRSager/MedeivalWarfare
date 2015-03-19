package mw.client.app.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import mw.client.app.ClientApplication;
import mw.client.controller.guimodel.DisplayUpdater;
import mw.client.controller.guimodel.ChoiceCenter.ChoiceType;
import mw.client.controller.model.NewStateApplier;
import mw.client.model.ModelTile;
import mw.shared.SharedColor;
import mw.shared.SharedCoordinates;
import mw.shared.SharedTile;

import org.minueto.MinuetoEventQueue;

public final class GUITest {
	

	//private static final Player PLAYER = new Player(SharedColor.YELLOW, "player");

	public final static int DEFAULT_MAP_WIDTH = 18;
	public final static int DEFAULT_MAP_HEIGHT = 18;

	public static void main(String[] args)
	{
		newGame();
		ClientApplication.startDisplay();
		
		waitABit();
		testUpdate(SharedColor.BLUE);
		waitABit();
		testUpdate(SharedColor.YELLOW);
		//waitABit();
		DisplayUpdater.showVillageResources(200, 200);
		List<String> crap = new ArrayList<String>();
		crap.add("crap1");
		crap.add("crap2");
		crap.add("crap3 is actually long");
		DisplayUpdater.displayChoice(ChoiceType.UNIT_ACTION, crap);
		DisplayUpdater.showEndTurnButton(true);
		
		//waitABit();
		ClientApplication.window.testAddTextField();
		
		
		while(true)
		{
			MinuetoEventQueue queue = ClientApplication.window.getEventQueue();
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
					new SharedCoordinates(r.nextInt(DEFAULT_MAP_HEIGHT), r.nextInt(DEFAULT_MAP_WIDTH)),
					SharedTile.Terrain.SEA, false,
					SharedTile.UnitType.NONE,
					SharedTile.VillageType.NONE, 0, 0);
		NewStateApplier.applyChanges(ClientApplication.game, newST);
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
		ClientApplication.newGame(newTiles);
	}


}