package mw.client.gui;

import java.util.Observable;
import java.util.Observer;

import org.minueto.window.MinuetoFrame;
import org.minueto.window.MinuetoWindow;

public class GameWindow implements Observer {
	private MinuetoWindow window;
	private MapDisplay md;
	
	public static void main(String[] args)
	{
		GameWindow gw = new GameWindow();
		gw.render();
		System.out.println("blah");
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
		window = new MinuetoFrame(500, 500, true);
		window.setVisible(true);
		mapDisp.setObserver(this);
	}
	
	public GameWindow()
	{
		this(new MapDisplay(10,10));
	}
	
	public void render()
	{
		md.renderMap(window);
	}
	
	public void update()
	{
		md.update();
		System.out.println("dfgcgv");
		render();
	}

	@Override
	public void update(Observable o, Object arg) {
		System.out.println("Window notified");
		render();
	}
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	/*public void renderGameWindow()
	{
		MinuetoWindow window = new MinuetoFrame(500, 500, true);
		window.setVisible(true);
	}*/
}
