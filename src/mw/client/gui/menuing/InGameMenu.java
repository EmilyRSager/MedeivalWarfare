package mw.client.gui.menuing;

import javax.swing.*;

import mw.client.controller.menuing.MenuControl;
import mw.client.controller.netmodel.UserActionSender;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InGameMenu
{

	private final JFrame window;
	private final Container pane;
	private final GridLayout gLayout;
	private final JButton exit;
	private final JButton save;
	
	public InGameMenu()
	{
		window = new JFrame("MENU");
		window.setResizable(false);
		pane = window.getContentPane();
		gLayout = new GridLayout(0, 1);
		pane.setLayout(gLayout);
		pane.setPreferredSize(new Dimension(250, 80));
		
		final InGameMenu dumbRef = this;
		
		exit = new JButton("EXIT");
		exit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e)
			{
				MenuControl.leaveGame();
				dumbRef.close();
			}
		});
		
		save = new JButton("SAVE");
		save.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e)
			{
				UserActionSender.singleton().sendSaveGame();
			}
		});
		
		pane.add(save);
		pane.add(exit);
		
		window.pack();
		window.setLocationRelativeTo(null);
		window.setVisible(true);
	}
	
	public void close()
	{
		this.window.dispose();
	}
	
	public static void main(String[] Args)
	{
		InGameMenu t = new InGameMenu();
	}
}
