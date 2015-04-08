package mw.client.gui.menuing;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.*;

import mw.client.controller.menuing.MenuControl;
import mw.client.controller.menuing.ScreenSwitcher;
import mw.client.controller.menuing.ScreenSwitcher.ScreenKind;
import mw.shared.SharedCreatedGame;
import mw.shared.SharedGameLobby;

public class GameLobbyWindow {

	private final JFrame window;
	private final JTabbedPane jtp;
	private final JPanel availTab;
	private final JPanel loadTab;
	
	public GameLobbyWindow(SharedGameLobby lobby)
	{
		window = new JFrame("Medieval Warfare Game Lobby");
		window.setResizable(false);
		jtp = new JTabbedPane();
		
		ArrayList<String> joinableGameNames = new ArrayList<String>();
		for (SharedCreatedGame game: lobby.getCreatedGames())
		{
			joinableGameNames.add(game.getGameName());
		}
		
		availTab = createTab(joinableGameNames, 0);
		loadTab = createTab(lobby.getLoadableGameNames(), 1);
		
		jtp.addTab("Active Games", availTab);
		jtp.addTab("Loadable Games", loadTab);
		
		window.getContentPane().add(jtp, BorderLayout.CENTER);
		
		window.pack();
		window.setLocationRelativeTo(null);
		window.setVisible(true);
	}
	
	private JPanel createTab(Collection<String> avail, int tab)
	{
		JPanel aTab = new JPanel();
		
		JLabel gLabel = new JLabel("Available Games:");
		
		JList<String> gameJList = new JList<String>(avail.toArray(new String[0]));
		JScrollPane sPane = new JScrollPane(gameJList);
		
		JButton create = new JButton("Create New Game");
		
		create.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e)
			{
				ScreenSwitcher.switchScreen(ScreenKind.GAME_CREATION);
			}
		});

		JPanel buttonContainer = new JPanel();
		buttonContainer.add(create);
		
		if(tab == 0)
		{
			JButton join = new JButton("Join Selected Game");
			
			join.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e)
				{
					MenuControl.gameSelected(gameJList.getSelectedValuesList().get(0));
				}
			});
			buttonContainer.add(join);
		}
		else if(tab == 1)
		{
			JButton launch = new JButton("Launch Selected Game");
			
			launch.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e)
				{
					MenuControl.gameSelected(gameJList.getSelectedValuesList().get(0));
				}
			});
			buttonContainer.add(launch);
		}
		
		BorderLayout bL = new BorderLayout();
		aTab.setLayout(bL);
		aTab.add(gLabel, BorderLayout.PAGE_START);
		aTab.add(sPane, BorderLayout.CENTER);
		aTab.add(buttonContainer, BorderLayout.PAGE_END);
		
		return aTab;
	}
	
	public void close()
	{
		this.window.dispose(); 
	}
	
	public static void main(String[] Args)
	{
		Set<SharedCreatedGame> empty = new HashSet<SharedCreatedGame>();
		SharedGameLobby test = new SharedGameLobby(empty);
		GameLobbyWindow t = new GameLobbyWindow(test, test);
	}
}
