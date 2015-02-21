package mw.server.network;

import java.awt.datatransfer.StringSelection;

import com.google.gson.Gson;

import mw.server.gamelogic.StructureType;
import mw.server.gamelogic.Tile;

public class tester {
	public static void main(String[] args) {
		Tile xTile = new Tile(StructureType.NO_STRUCT, 3, 4);
		Gson gson = new Gson();
		
		String sample = gson.toJson(xTile);
		System.out.println(sample);
		
		String[] list = sample.split("\"");
		
//		for(String s: list){
//			System.out.println(s);
//		}
		
		System.out.println(list[3]);
	}
}
