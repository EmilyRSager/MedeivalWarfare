package mw.client.gui;

import java.util.Observable;
import java.util.Observer;

import mw.client.app.MainApplication;
import mw.client.gui.api.AbstractButton;
import mw.client.gui.api.ExtendedMinuetoColor;
import mw.client.gui.api.GridLayout;
import mw.client.gui.api.HorizontalLayout;
import mw.client.gui.api.TextDisplay;
import mw.client.gui.api.VerticalLayout;

import org.minueto.MinuetoColor;
import org.minueto.MinuetoEventQueue;
import org.minueto.handlers.MinuetoKeyboardHandler;
import org.minueto.handlers.MinuetoMouseHandler;
import org.minueto.window.MinuetoFrame;

public class GameWindow implements Observer {
	
	public static final MinuetoColor BACKGROUND_COLOR = ExtendedMinuetoColor.mixColors(MinuetoColor.BLACK, MinuetoColor.WHITE, 0.10);
	public static final int DEFAULT_FRAME_WIDTH = 500;
	public static final int DEFAULT_FRAME_HEIGHT = 525;
	public static final int DEFAULT_MAP_WIDTH = 600;
	public static final int DEFAULT_MAP_HEIGHT = 600;
	
	private final MinuetoFrame window;
	private final MinuetoEventQueue queue;
	private MapDisplay md;
	private MapComponent mapComp;
	private AbstractButton button;
	private VerticalLayout windowLayout;
	
	//private boolean displaying = false;
	//private AbstractWindowComponent mapComp;
	//private AbstractWindowComponent textComp;.
	
	/* ========================
	 * 		Constructors
	 * ========================
	 */

	
	public GameWindow(MapDisplay mapDisp)
	{
		md = mapDisp;
		queue = new MinuetoEventQueue();
		mapComp = new MapComponent(0, 0, DEFAULT_MAP_WIDTH, DEFAULT_MAP_HEIGHT, md);
		button = new AbstractButton(0,600,"Click me !") {
			public void buttonClick(int mouseButton)
			{
				if (mouseButton==1)
					System.out.println("I am clicked !");
			}
		};
		
		HorizontalLayout botLayout = new HorizontalLayout(0, 0, 2);
		botLayout.addComponent(button);
		botLayout.addComponent(new TextDisplay(0, 0, "Layouts are the best !"));
		
		windowLayout = new VerticalLayout(0, 0, 2);
		windowLayout.addComponent(mapComp);
		//windowLayout.addComponent(button);
		windowLayout.addComponent(botLayout);
		
		window = new MinuetoFrame(windowLayout.getWidth(), windowLayout.getHeight(), true);
		
		mapComp.setWindow(this);
		this.registerMouseHandler(button);
		button.addObserver(this);
		
		window.setVisible(true);
		
		
		//window = new MinuetoFrame();
		//window.setVisible(true);
		//mapComp = new ContainerComponent(0, 0, md.getWidth(), md.getHeight(), md);
		
		
		//mapDisp.setObserver(this);
		/*MinuetoMouseHandler mouseHand = new MinuetoMouseHandler()
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
					mapComp.handleMouseClick(x, y, button);
					textComp.handleMouseClick(x, y, button);
					/*ImageTile clickedTile = mapDisp.getClickedTile(x, y);
					ModelTile clickedModelTile = ModelViewMapping.singleton().getModelTile(clickedTile);
					if(clickedModelTile != null)
					{
						clickedModelTile.setColor(SharedColor.RED);
						clickedModelTile.notifyObservers();
					}
				}
			};*/
		//this.registerMouseHandler(mouseHand, queue);
	}
	
	public GameWindow()	// only there for old testing purpose
	{
		this(new MapDisplay(MainApplication.DEFAULT_MAP_WIDTH, MainApplication.DEFAULT_MAP_HEIGHT));
	}
	
	
	/* ==========================
	 * 		Public methods
	 * ==========================
	 */

	
	/*public void startDisplay() {
		setVisible(true);
		displaying=true;
		Thread displayThread = new Thread() {
			public void run() {
				
			}
		};
		displayThread.start();
	}*/
	
	
	public MinuetoEventQueue getEventQueue()
	{
		return this.queue;
	}
	
	public void render()
	{
		window.clear(BACKGROUND_COLOR);
		/*mapComp.drawOn(this);
		button.drawOn(this);*/
		windowLayout.drawOn(window);
		//super.render();
		window.render();
	}
	
	public void update()	// old testing purpose method
	{
		md.update();
		render();
	}

	public void registerMouseHandler(MinuetoMouseHandler h)
	{
		window.registerMouseHandler(h, queue);
	}
	

	public void registerKeyboardHandler(MinuetoKeyboardHandler h) 
	{
		window.registerKeyboardHandler(h, queue);
	}
	
	/* ==========================
	 * 		Private methods
	 * ==========================
	 */


	/* ==========================
	 * 		Inherited methods
	 * ==========================
	 */

	@Override
	public void update(Observable o, Object arg) {
		render();
	}


	/* ========================
	 * 		Static methods
	 * ========================
	 */
	
	
	
	
	
	
	
	
	
	
	

	
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
	
	
	
	
	
	
	
	
}
