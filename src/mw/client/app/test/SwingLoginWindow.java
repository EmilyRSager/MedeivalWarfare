package mw.client.app.test;

import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.*;

import mw.client.controller.menuing.MenuActionSender;

public class SwingLoginWindow {

	public static void main(String[] args)
	{
		JFrame window = new JFrame("Swing Login Window");
		Container pane = window.getContentPane();
		GridLayout gLayout = new GridLayout(0,2);
		pane.setLayout(gLayout);
		
		JMenuBar menuBar = new JMenuBar();
		JMenu file = new JMenu("File");
		file.setMnemonic(KeyEvent.VK_F);
		JMenuItem exitItem = new JMenuItem("Exit");
		exitItem.setToolTipText("Exit application");
		file.add(exitItem);
		menuBar.add(file);
		window.setJMenuBar(menuBar);
		
		JLabel uLabel = new JLabel("Username:");
		JTextField username = new JTextField(20);
		JLabel pLabel = new JLabel("Password:");
		JPasswordField password = new JPasswordField(10);
		JButton login = new JButton("Login");
		
		login.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e)
			{
				boolean status = MenuActionSender.tryLogin(username.getText(), password.getText());
				if (status)
					System.out.println("You successfuly logged in !");
				else
					System.out.println("You didn't login.");
			}
		});
		
		pane.add(uLabel);
		pane.add(username);
		pane.add(pLabel);
		pane.add(password);
		pane.add(login);
		
		window.pack();
		window.setVisible(true);
		
	}
	
}
