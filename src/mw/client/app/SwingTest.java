package mw.client.app;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;

import org.minueto.MinuetoColor;
import org.minueto.window.MinuetoPanel;

public final class SwingTest {

	public static void main(String[] args) {
		JFrame window = new JFrame("Test window");
		MinuetoPanel minu = new MinuetoPanel(100, 100);
		
		window.setContentPane(minu);
		
		window.pack();
		
		window.setVisible(true);
		System.out.println("set window visible");
		minu.setVisible(true);
		System.out.println("set visible");
		//minu.setVisible(true);
		
		minu.drawLine(MinuetoColor.BLACK, 0, 0, 50, 50);
		System.out.println("finished");
	}

}