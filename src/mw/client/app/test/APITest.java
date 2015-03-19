package mw.client.app.test;

import org.minueto.window.MinuetoFrame;

import mw.client.gui.api.components.AbstractButton;
import mw.client.gui.api.components.TextDisplay;
import mw.client.gui.api.interactive.TextField;
import mw.client.gui.api.layouts.GridLayout;
import mw.client.gui.api.layouts.HorizontalLayout;
import mw.client.gui.api.layouts.VerticalLayout;

public final class APITest {

	public static void main(String[] args) {
		MinuetoFrame frame = new MinuetoFrame(300, 100, true);
		frame.setVisible(true);
		
		VerticalLayout mainLayout = new VerticalLayout(3);
		
		GridLayout fieldsLayout = new GridLayout(2, 2);
		HorizontalLayout usernameLayout = new HorizontalLayout(2);
		HorizontalLayout pwdLayout = new HorizontalLayout(2);
		
		TextField usernameField = new TextField(150);
		fieldsLayout.addComponent(new TextDisplay("Username :"), 0, 0);
		fieldsLayout.addComponent(usernameField, 0, 1);
		//mainLayout.addComponent(usernameLayout, 0);
		
		TextField pwdField = new TextField(150);
		fieldsLayout.addComponent(new TextDisplay("Password :"), 1, 0);
		fieldsLayout.addComponent(pwdField, 1, 1);
		//mainLayout.addComponent(pwdLayout, 1);
		mainLayout.addComponent(fieldsLayout, 0);
		
		mainLayout.addComponent(new AbstractButton("Login") {
			
			@Override
			public void buttonClick(int mouseButton)
			{
				System.out.println("Username = "+usernameField.getText());
				System.out.println("Password = "+pwdField.getText());
			}
		}, 2);
		
		mainLayout.drawOn(frame);
		frame.render();
	}

}