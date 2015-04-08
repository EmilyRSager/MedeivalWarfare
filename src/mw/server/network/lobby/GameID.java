package mw.server.network.lobby;

import java.io.Serializable;
import java.util.Collection;
import java.util.UUID;

import mw.server.gamelogic.state.Game;

/**
 * Provides a wrapping of game classes with information necessary to save and load games.
 */
public class GameID implements Serializable{
	
	private String aName;
	private Game aGame;
	//list of accounts playing that game
	private Collection<UUID> aParticipantAccountIDs;
	
	public GameID(Game pGame, String pName, Collection<UUID> pParticipantAccountIDs){
		this.aGame = pGame;
		this.aName = pName;
		aParticipantAccountIDs = pParticipantAccountIDs;
	}
	
	public String getName() {
		return aName;
	}
	
	public Game getGame() {
		return aGame;
	}

	public Collection<UUID> getParticipantAccountIDs() {
		return aParticipantAccountIDs;
	}
}
