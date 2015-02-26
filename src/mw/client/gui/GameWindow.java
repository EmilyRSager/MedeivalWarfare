package mw.client.gui;

import java.util.Observable;
import java.util.Observer;

import mw.client.app.MainApplication;
import mw.client.controller.ModelViewMapping;
import mw.client.model.ModelTile;
import mw.shared.SharedColor;

import org.minueto.MinuetoEventQueue;
import org.minueto.handlers.MinuetoMouseHandler;
import org.minueto.window.MinuetoFrame;
import org.minueto.window.MinuetoWindow;

public class GameWindow implements Observer {
	private MinuetoWindow window;
	private MapDisplay md;
	public static final int DEFAULT_FRAME_WIDTH = 500;
	public static final int DEFAULT_FRAME_HEIGHT = 525;
	private MinuetoEventQueue queue;
	
	/*public static void main(String[] args)
	{
		GameWindow gw = new GameWindow();
		gw.render();
		try 
		{
			Thread.sleep(500);
		} 
		catch (InterruptedException e) 
		{
			e.printStackTrace();
		}
		
		gw.update();
	}*/
	
	public GameWindow(MapDisplay mapDisp)
	{
		md = mapDisp;
		window = new MinuetoFrame(mapDisp.getWidth(), mapDisp.getHeight(), true);
		window.setVisible(true);
		mapDisp.setObserver(this);
		MinuetoMouseHandler mouseHand = new MinuetoMouseHandler()
			{
				public void handleMouseMove(int x, int y)
				{
					//Not being used	
				}
				public void handleMousePress(int x, int y, int button)
				{
					//Not being used
				}
				public void handleMouseRelease(int x, int y, int button)
				{
					ImageTile clickedTile = mapDisp.getClickedTile(x, y);
					ModelTile clickedModelTile = ModelViewMapping.singleton().getModelTile(clickedTile);
					if(clickedModelTile != null)
					{
						clickedModelTile.setColor(SharedColor.RED);
						clickedModelTile.notifyObservers();
					}
				}
			};
		queue = new MinuetoEventQueue();
		window.registerMouseHandler(mouseHand, queue);
	}
	
	public MinuetoEventQueue getEventQueue()
	{
		return this.queue;
	}
	
	public GameWindow()	// only there for old testing purpose
	{
		this(new MapDisplay(MainApplication.DEFAULT_MAP_WIDTH, MainApplication.DEFAULT_MAP_HEIGHT));
	}
	
	public void render()
	{
		md.renderMap(window);
	}
	
	public void update()	// old testing purpose method
	{
		md.update();
		render();
	}

	@Override
	public void update(Observable o, Object arg) {
		render();
	}
}
