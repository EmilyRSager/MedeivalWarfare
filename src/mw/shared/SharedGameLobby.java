package mw.shared;

import java.util.HashSet;
import java.util.Set;

public class SharedGameLobby {
	private Set<SharedCreatedGame> aCreatedGames;
	private Set<String> aLoadableGameNames;
	
	public SharedGameLobby(Set<SharedCreatedGame> pCreatedGames){
		this(pCreatedGames, new HashSet<String>());
	}
	
	public SharedGameLobby(Set<SharedCreatedGame> pCreatedGames, Set<String> pLoadableGameNames) {
		aCreatedGames = pCreatedGames;
		aLoadableGameNames = pLoadableGameNames;
	}
	
	public Set<SharedCreatedGame> getCreatedGames(){
		return aCreatedGames;
	}
	
	public Set<String> getLoadableGameNames(){
		return aLoadableGameNames;
	}
}
