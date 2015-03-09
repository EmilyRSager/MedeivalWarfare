package mw.client.controller;

import java.util.List;

import org.minueto.MinuetoColor;

import mw.client.gui.ImageTile;
import mw.client.gui.api.ExtendedMinuetoColor;
import mw.client.model.ModelTile;
import mw.shared.SharedTile.*;

public final class DisplayUpdater {
	
	public static void updateImageTile(ImageTile tileDisp, MinuetoColor color,
			Terrain terrain, VillageType vill, UnitType unit)
	{
		tileDisp.updateImage(color, terrain, vill, unit);
	}

	public static void setSelected(ImageTile t, boolean selected, MinuetoColor c)
	{
		if(selected)
		{
			t.drawBorder(MinuetoColor.WHITE);
		}
		else
		{
			t.drawBorder(ExtendedMinuetoColor.GREY);
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