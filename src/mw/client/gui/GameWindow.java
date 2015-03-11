package mw.client.gui;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import mw.client.app.MainApplication;
import mw.client.controller.ActionInterpreter;
import mw.client.controller.ChoiceCenter;
import mw.client.controller.ChoiceCenter.ChoiceType;
import mw.client.gui.api.AbstractButton;
import mw.client.gui.api.ExtendedMinuetoColor;
import mw.client.gui.api.GridLayout;
import mw.client.gui.api.HorizontalLayout;
import mw.client.gui.api.TextDisplay;
import mw.client.gui.api.VerticalLayout;
import mw.shared.SharedColor;

import org.minueto.MinuetoColor;
import org.minueto.MinuetoEventQueue;
import org.minueto.handlers.MinuetoKeyboardHandler;
import org.minueto.handlers.MinuetoMouseHandler;
import org.minueto.window.MinuetoFrame;

public class GameWindow implements Observer {
	
	public static final MinuetoColor BACKGROUND_COLOR = ExtendedMinuetoColor.mixColors(MinuetoColor.BLACK, MinuetoColor.WHITE, 0.10);
	public static final int DEFAULT_MAP_WIDTH = 1000;
	public static final int DEFAULT_MAP_HEIGHT = 600;
	
	private final MinuetoFrame window;
	private final MinuetoEventQueue queue;
	private MapDisplay md;
	private MapComponent mapComp;
	private VerticalLayout windowLayout;
	private HorizontalLayout controlBarLayout;
	
	/* ========================
	 * 		Constructors
	 * ========================
	 */

	
	public GameWindow(MapDisplay mapDisp)
	{
		md = mapDisp;
		queue = new MinuetoEventQueue();
		mapComp = new MapComponent(0, 0, DEFAULT_MAP_WIDTH, DEFAULT_MAP_HEIGHT, md);
		
		windowLayout = new VerticalLayout(0, 0, 2);
		controlBarLayout = new HorizontalLayout(0, 0, 200, 3);
		
		windowLayout.addComponent(mapComp, 0);
		windowLayout.addComponent(controlBarLayout, 1);
		window = new MinuetoFrame(windowLayout.getWidth(), windowLayout.getHeight(), true);
		
		mapComp.setWindow(this);
		windowLayout.setWindow(this);
		controlBarLayout.setWindow(this);
		window.setVisible(true);
		window.setTitle("Medieval Warfare");	
	}
	
	/* ==========================
	 * 		Public methods
	 * ==========================
	 */
	
	public MinuetoEventQueue getEventQueue()
	{
		return this.queue;
	}
	
	public void render()
	{
		window.clear(BACKGROUND_COLOR);
		windowLayout.drawOn(window);
		window.render();
	}

	public void registerMouseHandler(MinuetoMouseHandler h)
	{
		window.registerMouseHandler(h, queue);
	}
	

	public void registerKeyboardHandler(MinuetoKeyboardHandler h) 
	{
		window.registerKeyboardHandler(h, queue);
	}
	
	
	public void displayVillageResources(int gold, int wood)
	{
		VerticalLayout resourceLayout = new VerticalLayout(2);
		TextDisplay goldText = new TextDisplay("Gold: " + gold);
		TextDisplay woodText = new TextDisplay("Wood: " + wood);
		resourceLayout.addComponent(woodText);
		resourceLayout.addComponent(goldText);
		controlBarLayout.addComponent(resourceLayout, 0);
		resourceLayout.setWindow(this);
		this.render();
	}
	
	public void addChoiceLayout(ChoiceType choiceType, List<String> choices)
	{
		VerticalLayout choiceLayout = new VerticalLayout(choices.size() + 1);
		TextDisplay choiceTitle = new TextDisplay(ChoiceCenter.getChoiceTitle(choiceType));
		choiceLayout.addComponent(choiceTitle);
		for(String str : choices)//int i = 0; i < choices.size(); i++)
		{
			AbstractButton choiceButton = new AbstractButton(str)
				{				
					public void buttonClick(int mouseButton)
					{
						if (mouseButton==1)
						{
							System.out.println("I am clicked !");
							ActionInterpreter.singleton().notifyChoiceResult(choiceType, str);
						}
					}
				};
			this.registerMouseHandler(choiceButton);
			choiceLayout.addComponent(choiceButton);
		}
		switch (choiceType)
		{
		case VILLAGE_UPGRADE:
			controlBarLayout.addComponent(choiceLayout, 1);
			break;
		case UNIT_HIRE:
		case UNIT_ACTION:
			controlBarLayout.addComponent(choiceLayout, 2);
			break;
		}
		choiceLayout.setWindow(this);
		this.render();
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
		
}
