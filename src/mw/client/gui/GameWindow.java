package mw.client.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;
import javax.swing.JLabel;

import mw.client.app.MainApplication;
import mw.client.controller.ModelViewMapping;
import mw.client.model.ModelTile;
import mw.shared.SharedColor;

import org.minueto.MinuetoColor;
import org.minueto.MinuetoEventQueue;
import org.minueto.handlers.MinuetoMouseHandler;
import org.minueto.window.MinuetoFrame;
import org.minueto.window.MinuetoPanel;
import org.minueto.window.MinuetoWindow;

public class GameWindow implements Observer {
	private MinuetoFrame window;
	private MapDisplay md;
	private WindowComponent wComp;
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
		window = new MinuetoFrame(md.getWidth(), md.getHeight(), true);
		window.setVisible(true);
		wComp = new WindowComponent(0, 0, md.getWidth(), md.getHeight(), md);
		
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
					wComp.handleMouseClick(x, y, button);
					/*ImageTile clickedTile = mapDisp.getClickedTile(x, y);
					ModelTile clickedModelTile = ModelViewMapping.singleton().getModelTile(clickedTile);
					if(clickedModelTile != null)
					{
						clickedModelTile.setColor(SharedColor.RED);
						clickedModelTile.notifyObservers();
					}*/
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
		wComp.drawOn(window);
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
