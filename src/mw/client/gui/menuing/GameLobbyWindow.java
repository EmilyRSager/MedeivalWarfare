package mw.client.gui.menuing;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

import mw.client.controller.menuing.MenuControl;
import mw.client.controller.menuing.ScreenSwitcher;
import mw.client.controller.menuing.ScreenSwitcher.ScreenKind;
import mw.shared.SharedCreatedGame;
import mw.shared.SharedGameLobby;

public class GameLobbyWindow {

	private final JFrame window;
	private final Container pane;
	//private final BorderLayout bLayout;
	private final JLabel gLabel;
	private final List<String> games;
	private final JList<String> gameJList;
	private final JScrollPane sPane;
	private final JPanel buttonContainer;
	private final JButton create;
	private final JButton join;
	
	public GameLobbyWindow(SharedGameLobby lobby)
	{
		window = new JFrame("Medieval Warfare Game Lobby");
		pane = window.getContentPane();
		//bLayout = new BorderLayout(0,2);
		//pane.setLayout(bLayout);
		
		gLabel = new JLabel("Available Games:");
		games = new ArrayList<String>();
		for (SharedCreatedGame game: lobby.getCreatedGames())
		{
			games.add(game.getGameName());
		}
		gameJList = new JList<String>(games.toArray(new String[0]));
		sPane = new JScrollPane(gameJList);
		
		create = new JButton("Create New Game");
		
		create.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e)
			{
				ScreenSwitcher.switchScreen(ScreenKind.GAME_CREATION);
			}
		});
		
		join = new JButton("Join Selected Game");
		
		join.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e)
			{
				MenuControl.gameSelected(gameJList.getSelectedValuesList().get(0));
			}
		});
		
		buttonContainer = new JPanel();
		buttonContainer.add(create);
		buttonContainer.add(join);
		
		pane.add(gLabel, BorderLayout.PAGE_START);
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
		GameLobbyWindow t = new GameLobbyWindow(testArray);
	}
}
