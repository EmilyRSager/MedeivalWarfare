package mw.client.gui.menuing;

import javax.swing.*;

import mw.client.controller.menuing.MenuControl;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CreateGameWindow
{

	private final JFrame window;
	private final Container pane;
	private final GridLayout gLayout;
	private final JLabel nameLabel;
	private final JTextField gameName;
	private final JLabel playerLabel;
	private final JComboBox<Integer> numPlayers;
	private final JButton create;
	private final JButton back;
	private final Integer[] numbers = {2, 3, 4};
	
	public CreateGameWindow()
	{
		window = new JFrame("Create a New Game");
		pane = window.getContentPane();
		gLayout = new GridLayout(0, 2);
		pane.setLayout(gLayout);
		
		nameLabel = new JLabel("Name your game: ");
		gameName = new JTextField();
		playerLabel = new JLabel("Number of players: ");
		numPlayers = new JComboBox<Integer>(numbers);
		
		create = new JButton("Create your game!");
		create.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e)
			{
				MenuControl.tryCreateGame(gameName.getText(), (int) numPlayers.getSelectedItem());
			}
		});
		
		back = new JButton("Go back to games lobby");
		back.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e)
			{
				MenuControl.openGameLobby();
			}
		});
		
		pane.add(nameLabel);
		pane.add(gameName);
		pane.add(playerLabel);
		pane.add(numPlayers);
		pane.add(back);
		pane.add(create);
		
		window.pack();
		window.setLocationRelativeTo(null);
		window.setVisible(true);
	}
	
	public static void main(String[] Args)
	{
		CreateGameWindow test = new CreateGameWindow();
	}
}