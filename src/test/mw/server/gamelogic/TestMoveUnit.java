package test.mw.server.gamelogic;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collection;

import mw.server.gamelogic.Color;
import mw.server.gamelogic.Game;
import mw.server.gamelogic.GameController;
import mw.server.gamelogic.Player;
import mw.server.gamelogic.StructureType;
import mw.server.gamelogic.Tile;
import mw.server.gamelogic.TooManyPlayersException;
import mw.server.gamelogic.Unit;
import mw.server.gamelogic.UnitType;
import mw.server.gamelogic.VillageType;
import mw.util.MultiArrayIterable;

import org.junit.Test;

import com.sun.media.jfxmedia.events.PlayerStateEvent.PlayerState;
import com.sun.org.glassfish.external.statistics.annotations.Reset;

public class TestMoveUnit {
	static Player p1 = new Player(), p2 = new Player();
	static Collection<Player> players = new ArrayList<Player>(2);
	static Game game;
	static Unit unit;
	
	public static void initializeMembers(){
		players.add(p1);
		players.add(p2);
		try {
			game = new Game(players, 5, 5);
		} catch (TooManyPlayersException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		unit = new Unit(UnitType.NO_UNIT);
	}
	
	public static void emptyTile(Tile pTile){
		pTile.setNeutral();
		pTile.setStructureType(StructureType.NO_STRUCT);
		pTile.setVillageType(VillageType.NO_VILLAGE);
		pTile.setUnit(null);
	}
	
	public static void emptyTiles(Tile[][] pTiles){
		for(Tile tile : MultiArrayIterable.toIterable(pTiles)){
			emptyTile(tile);
		}
	}
	
	@Test
	public void testMoveOnTree() {
		initializeMembers();
		Tile[][] tiles = game.getGameTiles();
		emptyTiles(tiles);
		
		for(UnitType unitType : UnitType.values()){
			tiles[0][0].setUnit(unit);
			unit.setUnitType(unitType);
			//set tiles to same color
			tiles[0][0].setColor(Color.BLUE);
			tiles[0][1].setColor(Color.BLUE);
			
			GameController.moveUnit(game, 0, 0, 0, 1);
			
			System.out.println(tiles[0][0].toString());
			System.out.println(tiles[0][1].toString());
			System.out.println();
			
			emptyTiles(tiles);
		}
	}
}
