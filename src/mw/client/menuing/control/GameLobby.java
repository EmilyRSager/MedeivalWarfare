package mw.client.menuing.control;

import java.awt.Container;
import java.util.List;

import javax.swing.*;

public class GameLobby {

	private JFrame window;
	private Container pane;
	private JMenuBar menuBar;
	private JButton launch;
	private JButton exit;
	
	public GameLobby(List<String> players)
	{
		window = new JFrame("Medieval Warfare Game Lobby");
		pane = window.getContentPane();
		menuBar = createMenuBar();
	}

	private JMenuBar createMenuBar() 
	{
		JMenuBar m = new JMenuBar();
		return null;
	}
	
	
}
