package mw.client.controller;

import org.minueto.MinuetoColor;

import mw.client.gui.ImageTile;
import mw.client.model.ModelTile;

public final class DisplayUpdater {
	
	public static void updateImageTile(ImageTile tileDisp, MinuetoColor color,
			ModelTile.Terrain terrain, ModelTile.StructureType struct, ModelTile.UnitType unit)
	{
		tileDisp.updateColor(color);
	}

	public static void setSelected(ImageTile t, boolean selected)
	{
		// TODO
	}
	
}