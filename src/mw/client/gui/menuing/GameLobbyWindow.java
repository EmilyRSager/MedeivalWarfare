package mw.client.gui.menuing;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.*;

public class GameLobbyWindow {

	private final JFrame window;
	private final Container pane;
	//private final BorderLayout bLayout;
	private final JLabel gLabel;
	private final JList<String> gameList;
	private final JScrollPane sPane;
	private final JPanel buttonContainer;
	private final JButton create;
	private final JButton join;
	
	public GameLobbyWindow(String[] games)
	{
		window = new JFrame("Medieval Warfare Game Lobby");
		pane = window.getContentPane();
		//bLayout = new BorderLayout(0,2);
		//pane.setLayout(bLayout);
		
		gLabel = new JLabel("Available Games:");
		gameList = new JList<String>(games);
		sPane = new JScrollPane(gameList);
		
		create = new JButton("Create New Game");
		
		create.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e)
			{
				//TO DO
			}
		});
		
		join = new JButton("Join Selected Game");
		
		join.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e)
			{
				//TO DO
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
	
	public static void main(String[] Args)
	{
		String[] testArray = {"one", "two", "three", "four", "five", "six", "seven"};
		GameLobbyWindow t = new GameLobbyWindow(testArray);
	}
}
