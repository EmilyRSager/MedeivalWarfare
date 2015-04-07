package mw.server.gamelogic.state;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

/**
 * Provides a wrapping of game classes with information necessary to save and load games.
 */
public class GameID {
	
	private String aName;
	private Game aGame;
	private Collection<UUID> aListOfAccountUUIDs;
	
	public GameID(Game pGame, String pName, Collection<UUID> pListOfAccountUUIDs){
		this.aGame = pGame;
		this.aName = pName;
		aListOfAccountUUIDs = pListOfAccountUUIDs;
	}
	
	public String getaName() {
		return aName;
	}
	
	public Game getaGame() {
		return aGame;
	}
	

	public Collection<UUID> getaListOfAccountUUIDs() {
		return aListOfAccountUUIDs;
	}

}
