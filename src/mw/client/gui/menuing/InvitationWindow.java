package mw.client.gui.menuing;

import javax.swing.*;

import mw.client.controller.menuing.MenuControl;
import mw.shared.SharedCreatedGame;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;

public class InvitationWindow {

	private final JFrame window;
	private final Container pane;
	private final JLabel invite;
	private final JPanel buttonContainer;
	private final JButton accept;
	private final JButton decline;
	
	public InvitationWindow(SharedCreatedGame game)
	{
		window = new JFrame("Game Invitation");
		pane = window.getContentPane();
		
		invite = new JLabel(game.getParticipatingPlayers().iterator().next() + " has invited you to the game: " + game.getGameName());
		invite.setFont(new Font("Courrier New", Font.BOLD, 15));
		
		accept = new JButton("Accept Invitation");
		accept.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e)
			{
				MenuControl.inviteAccepted(game.getGameName());
			}
		});
		
		decline = new JButton("Decline Invitation");
		InvitationWindow ref = this;
		decline.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e)
			{
				ref.window.dispose();
			}
		});
		
		buttonContainer = new JPanel();
		buttonContainer.add(accept);
		buttonContainer.add(decline);
		
		pane.add(invite, BorderLayout.CENTER);
		pane.add(buttonContainer, BorderLayout.PAGE_END);
		
		window.setResizable(false);
		window.pack();
		window.setLocationRelativeTo(null);
		window.setVisible(true);
	}
	
	public void close()
	{
		this.window.dispose();
	}
}
