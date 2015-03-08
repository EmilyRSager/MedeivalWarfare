package mw.client.controller;

import java.util.List;

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
		if(selected)
		{
			t.setBorderSelected(t.getTileImage());
		}
		else
		{
			t.setImage(t.getTileImage());
		}
	}

	public static void displayChoice(String choiceName, List<String> choices)
	{
		// TODO
	}

	public static void showVillageResources(int gold, int wood)
	{
		// TODO
	}
	
}