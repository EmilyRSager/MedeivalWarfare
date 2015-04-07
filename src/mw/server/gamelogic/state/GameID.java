package mw.server.gamelogic.state;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

public class GameID {
	
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
