package mw.client.gui;

import java.util.Observable;
import java.util.Observer;

import org.minueto.window.MinuetoFrame;
import org.minueto.window.MinuetoWindow;

public class GameWindow implements Observer {
	private MinuetoWindow window;
	private MapDisplay md;
	public static final int DEFAULT_FRAME_WIDTH = 500;
	public static final int DEFAULT_FRAME_HEIGHT = 525;
	
	public static void main(String[] args)
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
	}
	
	public GameWindow(MapDisplay mapDisp)
	{
		md = mapDisp;
		window = new MinuetoFrame(DEFAULT_FRAME_WIDTH, DEFAULT_FRAME_HEIGHT, true);
		window.setVisible(true);
		mapDisp.setObserver(this);
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
