package mw.client.gui;

import java.util.Observable;
import java.util.Observer;

import mw.client.app.MainApplication;
import mw.client.gui.api.ExtendedMinuetoColor;
import mw.client.gui.api.TextDisplay;

import org.minueto.MinuetoColor;
import org.minueto.MinuetoEventQueue;
import org.minueto.window.MinuetoFrame;

public class GameWindow extends MinuetoFrame implements Observer {
	
	public static final MinuetoColor BACKGROUND_COLOR = ExtendedMinuetoColor.mixColors(MinuetoColor.BLACK, MinuetoColor.WHITE, 0.10);
	
	//private MinuetoFrame window;
	private MapDisplay md;
	//private AbstractWindowComponent mapComp;
	//private AbstractWindowComponent textComp;.
	private MapComponent mapComp;
	private TextDisplay textComp;
	public static final int DEFAULT_FRAME_WIDTH = 500;
	public static final int DEFAULT_FRAME_HEIGHT = 525;
	private MinuetoEventQueue queue;
	//private boolean displaying = false;
	
	/* ========================
	 * 		Constructors
	 * ========================
	 */

	
	public GameWindow(MapDisplay mapDisp)
	{
		super(600, 650, true);
		md = mapDisp;
		queue = new MinuetoEventQueue();
		mapComp = new MapComponent(0, 0, 600, 600, md);
		textComp = new TextDisplay(0, 600, "This is a text component!");
		
		mapComp.setWindow(this);
		//this.setVisible(true);
		
		
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
	
	@Override
	public void render()
	{
		this.clear(BACKGROUND_COLOR);
		mapComp.drawOn(this);
		textComp.drawOn(this);
		super.render();
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
	
	/* ==========================
	 * 		Private methods
	 * ==========================
	 */


	/* ==========================
	 * 		Inherited methods
	 * ==========================
	 */


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
