package mw.client.app;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.LayoutManager;

import javax.swing.AbstractButton;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.minueto.MinuetoColor;
import org.minueto.window.MinuetoPanel;

public final class SwingTest {

	public static void main(String[] args) {
		JFrame window = new JFrame("Test window");
		Container pane = window.getContentPane();
		pane.setLayout(new BoxLayout(pane, BoxLayout.PAGE_AXIS));
		pane.add(new JButton("End turn"));
		pane.add(new JLabel("Gold : 200"));
		pane.add(new JLabel("Wood : 200"));
		
		window.pack();
		
		window.setVisible(true);
	}

}