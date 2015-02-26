package mw.client.controller;

import mw.client.model.Game;


/**
 * The CurrentClientState controller holds informations about the current state of the system
 * that are system-wide.
 * @author Hugo Kapp
 *
 */
public final class CurrentClientState {

	private static Game currentGame = null;
	
	
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
	
}