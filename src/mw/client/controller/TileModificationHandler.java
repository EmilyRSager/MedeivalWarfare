package mw.client.controller;

import java.util.Observable;
import java.util.Observer;

import mw.client.gui.ImageTile;
import mw.client.model.ModelTile;
import mw.shared.SharedColor;

public final class TileModificationHandler implements Observer {
	
	/* ========================
	 * 		Static methods
	 * ========================
	 */
	
	public static void displayTile(ModelTile modelTile, ImageTile viewTile)
	{
		SharedColor newColor = ModelQuerier.getTileColor(modelTile);
		viewTile.updateColor(ModelGUITranslator.translateToMinuetoColor(newColor));
	}
	
	
	/* ========================
	 * 		Public methods
	 * ========================
	 */
	
	
	@Override
	public void update(Observable arg0, Object arg1) {
		ModelTile modifiedTile = (ModelTile)arg0;
		ImageTile tileDisplay = ModelViewMapping.singleton().getTileDisplay(modifiedTile);
		TileModificationHandler.displayTile(modifiedTile, tileDisplay);
	}

}
