package mw.client.app.test;

import mw.client.gui.api.components.TextDisplay;

import org.minueto.MinuetoColor;
import org.minueto.image.MinuetoText;
import org.minueto.window.MinuetoFrame;

public final class APITest {

	public static void main(String[] args) {
		MinuetoFrame frame = new MinuetoFrame(100, 100, true);
		frame.setVisible(true);
		//TextDisplay label = new TextDisplay(0, 0, 100, 100, "Hayo");
		//label.drawOn(frame);
		MinuetoText label = new MinuetoText("Hoho",TextDisplay.DEFAULT_FONT, MinuetoColor.BLACK);
		frame.draw(label, 0, 0);
		frame.render();
	}

}