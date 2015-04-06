package mw.client.gui.menuing;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.event.DocumentListener;

import mw.client.controller.menuing.*;

public class CreateAccountWindow
{

	private final JFrame window;
	private final Container pane;
	private final GridLayout gLayout;
	private final JLabel uLabel;
	private final JLabel p1Label;
	private final JLabel p2Label;
	private final JTextField username;
	private final JPasswordField password1;
	private final JPasswordField password2;
	private final JButton create;
	
	public CreateAccountWindow() 
	{
		window = new JFrame("Create a Medieval Warfare Account");
		pane = window.getContentPane();
		gLayout = new GridLayout(0,2);
		pane.setLayout(gLayout);
		
		uLabel = new JLabel("Username:");
		username = new JTextField(20);
		
		p1Label = new JLabel("Password:");
		password1 = new JPasswordField(10);
		
		p2Label = new JLabel("Confirm Password:");
		password2 = new JPasswordField(10);
		
		create = new JButton("Create Account");
		
		create.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e)
			{
				if(password1.getText().equals(password2.getText()))
				{
					MenuControl.tryCreateAccount(username.getText(), password1.getText());
					System.out.println("Account created succesfully!");
				}
				else
				{
					System.out.println("Account was not created.");
				}
			}
		});
		
		pane.add(uLabel);
		pane.add(username);
		pane.add(p1Label);
		pane.add(password1);
		pane.add(p2Label);
		pane.add(password2);
		pane.add(create);
		
		window.pack();
		window.setLocationRelativeTo(null);
		window.setVisible(true);
		/*class accountDocumentListener implements DocumentListener 
		{
			public void changeUpdate(DocumentEvent e)
			{
				changed();
			}
			public void removeUpdate(DocumentEvent e)
			{
				changed();
			}
			public void insertUpdate(DocumentEvent e)
			{
				changed();
			}
			
			public void changed()
			{
				if()
			}
		}*/
	}
	public void close()
	{
		this.window.dispose(); 
	}
	
	//for testing
	public static void main(String[] Args)
	{
		CreateAccountWindow test = new CreateAccountWindow();
	}
}
