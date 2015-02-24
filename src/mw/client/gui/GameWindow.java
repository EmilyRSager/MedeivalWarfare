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
	
	public GameWindow()
	{
		window = new MinuetoFrame(500, 500, true);
		window.setVisible(true);
		md = new MapDisplay();
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
