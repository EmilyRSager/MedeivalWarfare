package mw.server.gamelogic.state;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

/**
 * Provides a wrapping of game classes with information necessary to save and load games.
 */
public class GameID implements Serializable{
	
	private String aName;
	private Game aGame;
	//list of accounts playing that game
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
