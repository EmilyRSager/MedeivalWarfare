package mw.client.gui;

import org.minueto.window.MinuetoFrame;
import org.minueto.window.MinuetoWindow;

public class GameWindow {
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
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	/*public void renderGameWindow()
	{
		MinuetoWindow window = new MinuetoFrame(500, 500, true);
		window.setVisible(true);
	}*/
}
