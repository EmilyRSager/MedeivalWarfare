package mw.client.controller.guimodel;

import java.util.List;

import org.minueto.MinuetoColor;
import org.minueto.image.MinuetoImage;

import mw.client.controller.CurrentClientState;
import mw.client.controller.guimodel.ChoiceCenter.ChoiceType;
import mw.client.gui.api.extminueto.ExtendedMinuetoColor;
import mw.client.gui.window.GameWindow;
import mw.client.gui.window.ImageFileManager;
import mw.client.gui.window.ImageTile;
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
		GameWindow window = CurrentClientState.getCurrentGameWindow();
		window.addChoiceLayout(choiceType, choices);
	}

	public static void showVillageResources(int gold, int wood)
	{
		GameWindow window = CurrentClientState.getCurrentGameWindow();
		window.displayVillageResources(gold, wood);
	}
	
	public static void showEndTurnButton(boolean show)
	{
		GameWindow window = CurrentClientState.getCurrentGameWindow();
		if(show)
		{
			window.addEndTurnButton();
		}
		else
		{
			window.removeEndTurnButton();
		}
	}

	
	public static void clearInfos()
	{
		GameWindow window = CurrentClientState.getCurrentGameWindow();

		if (window != null) {
			window.removeAllChoices();
			window.hideVillageResources();
		}
	}
	
	public static void showGeneralMessage(String message)
	{
		GameWindow window = CurrentClientState.getCurrentGameWindow();
		window.showMessage(message);
	}
	
	public static void showFireButton(boolean show)
	{
		if (show)
			CurrentClientState.getCurrentGameWindow().addFireButton();
		else
			CurrentClientState.getCurrentGameWindow().removeFireButton();
	}
	
}