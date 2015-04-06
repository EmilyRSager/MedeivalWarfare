package mw.client.gui.menuing;

import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.*;

import mw.client.controller.menuing.MenuActionSender;

public class LoginWindow 
{
	private final JFrame window;
	private final Container pane;
	private final GridLayout gLayout;
	private final JLabel uLabel;
	private final JTextField username;
	private final JLabel pLabel;
	private final JTextField password;
	private final JButton login;
	private final JButton create;
	
	public LoginWindow()
	{
		window = new JFrame("Medieval Warfare Login Window");
		pane = window.getContentPane();
		gLayout = new GridLayout(0,2);
		pane.setLayout(gLayout);
		
		/*JMenuBar menuBar = new JMenuBar();
		JMenu file = new JMenu("File");
		file.setMnemonic(KeyEvent.VK_F);
		JMenuItem exitItem = new JMenuItem("Exit");
		exitItem.setToolTipText("Exit application");
		file.add(exitItem);
		menuBar.add(file);
		window.setJMenuBar(menuBar);
		*/
		
		uLabel = new JLabel("Username:");
		username = new JTextField(20);
		pLabel = new JLabel("Password:");
		password = new JPasswordField(10);
		login = new JButton("Login");
		
		login.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e)
			{
				MenuControl.tryLogin(username.getText(), password.getText());
			}
		});
		
		createAcc = new JButton("Create new account");
		
		createAcc.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e)
			{
				ScreenSwitcher.switchScreen(ScreenKind.CREATE_ACCOUNT);
			}
		});
		
		pane.add(uLabel);
		pane.add(username);
		pane.add(pLabel);
		pane.add(password);
		pane.add(login);
		pane.add(createAcc);
		
		window.pack();
		window.setVisible(true);
		
	}
	
	public void close()
	{
		this.window.dispose();
	}
	
}
