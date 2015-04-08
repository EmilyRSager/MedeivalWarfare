package mw.client.gui.api.components;

import java.util.ArrayList;
import java.util.List;

import org.minueto.MinuetoColor;
import org.minueto.MinuetoEventQueue;
import org.minueto.MinuetoFileException;
import org.minueto.handlers.MinuetoFocusHandler;
import org.minueto.handlers.MinuetoKeyboardHandler;
import org.minueto.handlers.MinuetoMouseHandler;
import org.minueto.handlers.MinuetoMouseWheelHandler;
import org.minueto.handlers.MinuetoWindowHandler;
import org.minueto.image.MinuetoImage;
import org.minueto.window.MinuetoFrame;
import org.minueto.window.MinuetoWindow;

public class ResizableWindow implements MinuetoWindow {

	private final String windowTitle;
	private MinuetoFrame window;
	private MinuetoEventQueue eventQueue;
	
	private List<MinuetoFocusHandler> focusHandlers;
	private List<MinuetoKeyboardHandler> keyboardHandlers;
	private List<MinuetoMouseHandler> mouseHandlers;
	private List<MinuetoMouseWheelHandler> mouseWheelsHandlers;
	private List<MinuetoWindowHandler> windowHandlers;

	/* ========================
	 * 		Constructors
	 * ========================
	 */
	
	public ResizableWindow(int width, int height, MinuetoEventQueue queue, String title)
	{
		window = new MinuetoFrame(width, height, true);
		windowTitle = title;
		eventQueue = queue;
		
		focusHandlers = new ArrayList<MinuetoFocusHandler>();
		keyboardHandlers = new ArrayList<MinuetoKeyboardHandler>();
		mouseHandlers = new ArrayList<MinuetoMouseHandler>();
		mouseWheelsHandlers = new ArrayList<MinuetoMouseWheelHandler>();
		windowHandlers = new ArrayList<MinuetoWindowHandler>();
	}
	
	public ResizableWindow(int width, int height, String title) 
	{
		this(width, height, new MinuetoEventQueue(), title);
	}



	/* ==========================
	 * 		Public methods
	 * ==========================
	 */
	
	
	public MinuetoEventQueue getQueue() {
		return eventQueue;
	}
	
	public void resize(int newWidth, int newHeight)
	{
		MinuetoFrame newWindow = new MinuetoFrame(newWidth, newHeight, true);
		if (newWindow.getWidth() != newWidth || newWindow.getHeight() != newHeight)
		{
			System.out.println("[ResizableWindow] need to re-recreate the window ?!?");
			newWindow = new MinuetoFrame(newWidth, newHeight, true);
		}
		
		for (MinuetoFocusHandler handler : focusHandlers) {
			window.unregisterFocusHandler(handler, eventQueue);
			newWindow.registerFocusHandler(handler, eventQueue);
		}
		for (MinuetoKeyboardHandler handler : keyboardHandlers) {
			window.unregisterKeyboardHandler(handler, eventQueue);
			newWindow.registerKeyboardHandler(handler, eventQueue);
		}
		for (MinuetoMouseHandler handler : mouseHandlers) {
			window.unregisterMouseHandler(handler, eventQueue);
			newWindow.registerMouseHandler(handler, eventQueue);
		}
		for (MinuetoMouseWheelHandler handler : mouseWheelsHandlers) {
			window.unregisterMouseWheelHandler(handler, eventQueue);
			newWindow.registerMouseWheelHandler(handler, eventQueue);
		}
		for (MinuetoWindowHandler handler : windowHandlers) {
			window.unregisterWindowHandler(handler, eventQueue);
			newWindow.registerWindowHandler(handler, eventQueue);
		}


		if (window.isVisible()) {
			newWindow.setVisible(true);
			newWindow.setWindowPosition(window.getPositionX(), window.getPositionY());
			newWindow.setTitle(windowTitle);
		}
		
		window.close();
		window = newWindow;
	}

	public void registerFocusHandler(MinuetoFocusHandler arg0)
	{
		focusHandlers.add(arg0);
		window.registerFocusHandler(arg0, eventQueue);
	}

	public void registerKeyboardHandler(MinuetoKeyboardHandler arg0)
	{
		keyboardHandlers.add(arg0);
		window.registerKeyboardHandler(arg0, eventQueue);
	}

	public void registerMouseHandler(MinuetoMouseHandler arg0)
	{
		mouseHandlers.add(arg0);
		window.registerMouseHandler(arg0, eventQueue);
	}

	public void registerMouseWheelHandler(MinuetoMouseWheelHandler arg0)
	{
		mouseWheelsHandlers.add(arg0);
		window.registerMouseWheelHandler(arg0, eventQueue);
	}

	public void registerWindowHandler(MinuetoWindowHandler arg0)
	{
		windowHandlers.add(arg0);
		window.registerWindowHandler(arg0, eventQueue);
	}
	
	
	public void unregisterFocusHandler(MinuetoFocusHandler arg0)
	{
		windowHandlers.remove(arg0);
		window.unregisterFocusHandler(arg0, eventQueue);
	}

	public void unregisterKeyboardHandler(MinuetoKeyboardHandler arg0)
	{
		keyboardHandlers.remove(arg0);
		window.unregisterKeyboardHandler(arg0, eventQueue);
	}

	public void unregisterMouseHandler(MinuetoMouseHandler arg0)
	{
		mouseHandlers.remove(arg0);
		window.unregisterMouseHandler(arg0, eventQueue);
	}

	public void unregisterMouseWheelHandler(MinuetoMouseWheelHandler arg0)
	{
		mouseWheelsHandlers.remove(arg0);
		window.unregisterMouseWheelHandler(arg0, eventQueue);
	}

	public void unregisterWindowHandler(MinuetoWindowHandler arg0)
	{
		windowHandlers.remove(arg0);
		window.unregisterWindowHandler(arg0, eventQueue);
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
	public void clear() {
		window.clear();
	}

	@Override
	public void clear(MinuetoColor arg0) {
		window.clear(arg0);
	}

	@Override
	public void draw(MinuetoImage arg0, int arg1, int arg2) {
		window.draw(arg0, arg1, arg2);
	}

	@Override
	public void drawLine(MinuetoColor arg0, int arg1, int arg2, int arg3, int arg4)	{
		window.drawLine(arg0, arg1, arg2, arg3, arg4);
	}

	@Override
	public int getHeight() {
		return window.getHeight();
	}

	@Override
	public int getWidth() {
		return window.getWidth();
	}

	@Override
	public void save(String arg0) throws MinuetoFileException {
		window.save(arg0);
	}

	@Override
	public void setPixel(int arg0, int arg1, MinuetoColor arg2) {
		window.setPixel(arg0, arg1, arg2);
	}

	@Override
	public void close() {
		window.close();
	}

	@Override
	public double getFrameRate() {
		return window.getFrameRate();
	}

	@Override
	public int getPositionX() {
		return window.getPositionX();
	}

	@Override
	public int getPositionY() {
		return window.getPositionY();
	}

	@Override
	public boolean isClosed() {
		return window.isClosed();
	}

	@Override
	public boolean isVisible() {
		return window.isVisible();
	}

	@Override
	public void registerFocusHandler(MinuetoFocusHandler arg0,
			MinuetoEventQueue arg1)
	{
		window.registerFocusHandler(arg0, arg1);
	}

	@Override
	public void registerKeyboardHandler(MinuetoKeyboardHandler arg0,
			MinuetoEventQueue arg1)
	{
		window.registerKeyboardHandler(arg0, arg1);
	}

	@Override
	public void registerMouseHandler(MinuetoMouseHandler arg0,
			MinuetoEventQueue arg1)
	{
		window.registerMouseHandler(arg0, arg1);
	}

	@Override
	public void registerMouseWheelHandler(MinuetoMouseWheelHandler arg0,
			MinuetoEventQueue arg1)
	{
		window.registerMouseWheelHandler(arg0, arg1);
	}

	@Override
	public void registerWindowHandler(MinuetoWindowHandler arg0,
			MinuetoEventQueue arg1)
	{
		window.registerWindowHandler(arg0, arg1);
	}

	@Override
	public void render() {
		window.render();
	}

	@Override
	public void setCursorVisible(boolean arg0) {
		window.setCursorVisible(arg0);
	}

	@Override
	public void setMaxFrameRate(double arg0) {
		window.setMaxFrameRate(arg0);
	}

	@Override
	public void setTitle(String arg0) {
		window.setTitle(arg0);
	}

	@Override
	public void setVisible(boolean arg0) {
		window.setVisible(arg0);
		if (arg0)
			window.setTitle(windowTitle);
	}

	@Override
	public void unregisterFocusHandler(MinuetoFocusHandler arg0,
			MinuetoEventQueue arg1)
	{
		window.unregisterFocusHandler(arg0, arg1);
	}

	@Override
	public void unregisterKeyboardHandler(MinuetoKeyboardHandler arg0,
			MinuetoEventQueue arg1)
	{
		window.unregisterKeyboardHandler(arg0, arg1);
	}

	@Override
	public void unregisterMouseHandler(MinuetoMouseHandler arg0,
			MinuetoEventQueue arg1)
	{
		window.unregisterMouseHandler(arg0, arg1);
	}

	@Override
	public void unregisterMouseWheelHandler(MinuetoMouseWheelHandler arg0,
			MinuetoEventQueue arg1)
	{
		window.unregisterMouseWheelHandler(arg0, arg1);
	}

	@Override
	public void unregisterWindowHandler(MinuetoWindowHandler arg0,
			MinuetoEventQueue arg1)
	{
		window.unregisterWindowHandler(arg0, arg1);
	}



	/* ========================
	 * 		Static methods
	 * ========================
	 */

}