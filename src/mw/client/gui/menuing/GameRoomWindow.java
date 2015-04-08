package mw.client.gui.menuing;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import mw.client.controller.menuing.MenuControl;
import mw.client.controller.menuing.ScreenSwitcher;
import mw.client.controller.menuing.ScreenSwitcher.ScreenKind;
import mw.shared.SharedCreatedGame;

public class GameRoomWindow {

	private final JFrame window;
	private final Container pane;
	private final JLabel gameLabel;
	private final JList<String> playerList;
	private final JScrollPane sPane;
	private final JPanel buttonContainer;
	private final JButton lobby;
	private final JButton launch;
	
	public GameRoomWindow(SharedCreatedGame createdGame)
	{
		window = new JFrame(createdGame.getGameName() + " Lobby");
		window.setResizable(false);
		pane = window.getContentPane();
		
		gameLabel = new JLabel(createdGame.getGameName());
		
		playerList = new JList<String>(createdGame.getParticipatingPlayers().toArray(new String[0]));
		sPane = new JScrollPane(playerList);
		
		lobby = new JButton("Return to Games Lobby");
		
		lobby.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e)
			{
				MenuControl.openGameLobby();
			}
		});
		
		launch = new JButton("Launch Game");
		
		launch.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e)
			{
				//TO DO 
			}
		});
		
		buttonContainer = new JPanel();
		
		buttonContainer.add(lobby);
		buttonContainer.add(launch);
		
		pane.add(gameLabel, BorderLayout.PAGE_START);
		pane.add(sPane, BorderLayout.CENTER);
		pane.add(buttonContainer, BorderLayout.PAGE_END);
		
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
		String[] testArray = {"one", "two", "three", "four", "five", "six", "seven"};
	//	GameRoomWindow t = new GameRoomWindow(testArray, "Poop game");
	}
}
