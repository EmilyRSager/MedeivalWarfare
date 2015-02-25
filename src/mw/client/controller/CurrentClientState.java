package mw.client.controller;

import mw.client.model.Game;

public final class CurrentClientState {

	private static Game currentGame = null;
	
	public static void setCurrentGame(Game game) {
		currentGame = game;
	}
	
	public static Game getCurrentGame() {
		return currentGame;
	}
	
}