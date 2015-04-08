package mw.client.gui.menuing;

import javax.swing.*;

import mw.client.controller.menuing.MenuControl;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MessageWindow {

	private final JFrame window;
	private final Container pane;
	private final JLabel messLabel;
	private final JButton ok;
	
	public MessageWindow(String message)
	{
		window =  new JFrame("Message");
		window.setResizable(false);
		pane = window.getContentPane();
		
		messLabel = new JLabel(message);
		
		JFrame dumbRef = window;
		ok = new JButton("OK");

		ok.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e)
			{
				dumbRef.dispose();
			}
		});
		
		pane.add(messLabel, BorderLayout.CENTER);
		pane.add(ok, BorderLayout.PAGE_END);
		
		window.pack();
		window.setLocationRelativeTo(null);
		window.setVisible(true);
	}
}
