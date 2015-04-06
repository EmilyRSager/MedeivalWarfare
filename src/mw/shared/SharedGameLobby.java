package mw.shared;

import java.util.Collection;
import java.util.Set;

public class SharedGameLobby {
	private Set<SharedCreatedGame> aCreatedGames;
	
	public SharedGameLobby(Set<SharedCreatedGame> pCreatedGames) {
		aCreatedGames = pCreatedGames;
	}
	
	public void addCreatedGame(SharedCreatedGame pSharedCreatedGame){
		aCreatedGames.add(pSharedCreatedGame);
	}
	
	public Set<SharedCreatedGame> getCreatedGames(){
		return aCreatedGames;
	}
}
