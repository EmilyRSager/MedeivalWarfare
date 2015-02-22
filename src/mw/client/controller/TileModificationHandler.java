package mw.client.controller;

import java.util.Observable;
import java.util.Observer;

import mw.client.model.Tile;

public final class TileModificationHandler implements Observer {
	
	@Override
	public void update(Observable arg0, Object arg1) {
		Tile modifiedTile = (Tile)arg0;
		TileDisplay tileDisplay = ModelViewMapping.singleton().getTileDisplay(modifiedTile);
		// TODO call the GUI interface to set the image
	}

}
