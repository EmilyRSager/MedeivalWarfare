package mw.client.controller;

import mw.client.gui.GameWindow;
import mw.client.model.Game;
import mw.shared.SharedColor;


/**
 * The CurrentClientState controller holds informations about the current state of the system
 * that are system-wide.
 * @author Hugo Kapp
 *
 */
public final class CurrentClientState {

	private static Game currentGame = null;
	private static GameWindow currentGameWindow = null;
	
	/**
	 * Changes the current game
	 * @param game the new game
	 */
	public static void setCurrentGame(Game game) {
		currentGame = game;
	}
	
	
	/**
	 * Gets the current game
	 * @return the current game
	 */
	public static Game getCurrentGame() {
		return currentGame;
	}
	
	public static void setCurrentPlayerColor(SharedColor color)
	{
		ModelQuerier.setCurrentPlayerColor(currentGame,color);
	}
	
	
	public static void setCurrentGameWindow(GameWindow window) {
		currentGameWindow = window;
	}
	
	public static GameWindow getCurrentGameWindow() {
		return currentGameWindow;
	}
	
}