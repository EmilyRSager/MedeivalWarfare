package mw.shared;

import java.util.Set;

public class SharedCreatedGame {
	private String aGameName;
	private Set<String> aParticipatingPlayers;
	
	public SharedCreatedGame(String pGameName, Set<String> pParticipatingPlayers) {
		aGameName = pGameName;
		aParticipatingPlayers = pParticipatingPlayers;
	}
	
	public Set<String> getParticipatingPlayers() {
		return aParticipatingPlayers;
	}
	
	public String getGameName(){
		return aGameName;
	}
}
