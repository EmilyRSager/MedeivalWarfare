package mw.client.controller;

import java.util.List;

import org.minueto.MinuetoColor;
import org.minueto.image.MinuetoImage;

import mw.client.gui.GameWindow;
import mw.client.gui.ImageFileManager;
import mw.client.gui.ImageTile;
import mw.client.gui.api.ExtendedMinuetoColor;
import mw.client.gui.api.VerticalLayout;
import mw.client.model.ModelTile.*;

public final class DisplayUpdater {
	
	public static void updateImageTile(ImageTile tileDisp, MinuetoColor color,
			Terrain terrain, StructureType struct, UnitType unit, boolean hasRoad)
	{
		MinuetoImage newImage = ImageFileManager.getTileImage(color, terrain, struct, unit, hasRoad);
		tileDisp.updateImage(newImage);
	}

	public static void setSelected(ImageTile t, boolean selected)
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

	public static void displayChoice(ChoiceType choiceType, List<String> choices)
	{
		VerticalLayout choiceLayout = GameWindow.createChoiceLayout(choiceType, choices);
		GameWindow window = CurrentClientState.getCurrentGameWindow();
		window.addChoiceComponent(choiceType, choiceLayout);
	}

	public static void showVillageResources(int gold, int wood)
	{
		GameWindow window = CurrentClientStat.getCurrentGameWindow();
		window.displayVillageResources(gold, wood);
	}
	
}