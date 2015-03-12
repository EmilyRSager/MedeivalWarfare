package mw.client.gui.window;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import mw.client.controller.ActionInterpreter;
import mw.client.controller.ChoiceCenter;
import mw.client.controller.ChoiceCenter.ChoiceType;
import mw.client.gui.api.components.AbstractButton;
import mw.client.gui.api.components.TextDisplay;
import mw.client.gui.api.extminueto.ExtendedMinuetoColor;
import mw.client.gui.api.layouts.HorizontalLayout;
import mw.client.gui.api.layouts.VerticalLayout;

import org.minueto.MinuetoColor;
import org.minueto.MinuetoEventQueue;
import org.minueto.handlers.MinuetoFocusHandler;
import org.minueto.handlers.MinuetoKeyboardHandler;
import org.minueto.handlers.MinuetoMouse;
import org.minueto.handlers.MinuetoMouseHandler;
import org.minueto.window.MinuetoFrame;

public class GameWindow implements Observer {
	
	public static final MinuetoColor BACKGROUND_COLOR = ExtendedMinuetoColor.mixColors(MinuetoColor.BLACK, MinuetoColor.WHITE, 0.10);
	public static final int DEFAULT_MAP_WIDTH = 1000;
	public static final int DEFAULT_MAP_HEIGHT = 600;
	public static final int CONTROL_LAYOUT_HEIGHT = 150;
	
	private final MinuetoFrame window;
	private final MinuetoEventQueue queue;
	private MapDisplay md;
	private MapComponent mapComp;
	private VerticalLayout windowLayout;
	private HorizontalLayout controlBarLayout;
	private AbstractButton endTurn;
	private List<AbstractButton> choiceButtonsList;
	
	/* ========================
	 * 		Constructors
	 * ========================
	 */

	
	public GameWindow(MapDisplay mapDisp)
	{
		md = mapDisp;
		queue = new MinuetoEventQueue();
		mapComp = new MapComponent(0, 0, DEFAULT_MAP_WIDTH, DEFAULT_MAP_HEIGHT, md);
		choiceButtonsList = new ArrayList<AbstractButton>();
		
		endTurn = new AbstractButton("End Turn")
		{
			public void buttonClick(int mouseButton)
			{
				if(mouseButton == MinuetoMouse.MOUSE_BUTTON_LEFT)
				{
					ActionInterpreter.singleton().handleEndTurn();
				}
			}
		};
		
		windowLayout = new VerticalLayout(0, 0, 3);
		controlBarLayout = new HorizontalLayout(0, 0, CONTROL_LAYOUT_HEIGHT, 3);
		
		windowLayout.addComponent(mapComp, 0);
		windowLayout.addComponent(controlBarLayout, 2);
		window = new MinuetoFrame(windowLayout.getWidth(), windowLayout.getHeight(), true);
		
		mapComp.setWindow(this);
		windowLayout.setWindow(this);
		//controlBarLayout.setWindow(this);
		window.setVisible(true);
		window.setTitle("Medieval Warfare");
		GameWindow dumbRef = this;
		window.registerFocusHandler(new MinuetoFocusHandler() {
			
			@Override
			public void handleLostFocus()
			{
				
			}
			
			@Override
			public void handleGetFocus()
			{
				dumbRef.render();
			}
		}, queue);
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
		//resourceLayout.setWindow(this);
		this.render();
	}
	
	public void addChoiceLayout(ChoiceType choiceType, List<String> choices)
	{
		System.out.println("Creating a choice with type "+choiceType);
		VerticalLayout choiceLayout = new VerticalLayout(choices.size() + 1);
		TextDisplay choiceTitle = new TextDisplay(ChoiceCenter.getChoiceTitle(choiceType));
		choiceLayout.addComponent(choiceTitle);
		for(String str : choices)//int i = 0; i < choices.size(); i++)
		{
			AbstractButton choiceButton = new AbstractButton(str)
				{				
					public void buttonClick(int mouseButton)
					{
						if (mouseButton == MinuetoMouse.MOUSE_BUTTON_LEFT)
						{
							System.out.println("Notifying for "+choiceType+" item "+str);
							ActionInterpreter.singleton().notifyChoiceResult(choiceType, str);
						}
					}
				};
			this.registerMouseHandler(choiceButton);
			choiceLayout.addComponent(choiceButton);
			choiceButtonsList.add(choiceButton);
		}
		switch (choiceType)
		{
		case VILLAGE_UPGRADE:
		case UNIT_UPGRADE:
			controlBarLayout.addComponent(choiceLayout, 1);
			break;
		case UNIT_HIRE:
		case UNIT_ACTION:
			controlBarLayout.addComponent(choiceLayout, 2);
			break;
		}
		//choiceLayout.setWindow(this);
		this.render();
	}
	
	public void addEndTurnButton()
	{
		this.registerMouseHandler(endTurn);
		HorizontalLayout hlayout = new HorizontalLayout(1);
		hlayout.addComponent(endTurn);
		windowLayout.addComponent(hlayout, 1);
		//windowLayout.addComponent(endTurn, 1);
		//this.render();
	}
	
	public void removeEndTurnButton()
	{
		window.unregisterMouseHandler(endTurn, queue);
		windowLayout.removeComponent(1);
		this.render();
	}
	
	public void removeAllChoices()
	{
		controlBarLayout.removeComponent(1);
		controlBarLayout.removeComponent(2);
		for (AbstractButton b : choiceButtonsList)
			window.unregisterMouseHandler(b, queue);
		choiceButtonsList = new ArrayList<AbstractButton>();
		render();
	}

	public void hideVillageResources()
	{
		controlBarLayout.removeComponent(0);
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

	@Override
	public void update(Observable o, Object arg) {
		render();
	}

	


	/* ========================
	 * 		Static methods
	 * ========================
	 */
		
}
