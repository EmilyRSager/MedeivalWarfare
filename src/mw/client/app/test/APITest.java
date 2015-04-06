package mw.client.app.test;

import org.minueto.MinuetoEventQueue;
import org.minueto.window.MinuetoFrame;

import mw.client.gui.api.components.AbstractButton;
import mw.client.gui.api.components.BlockComponent;
import mw.client.gui.api.components.ResizableWindow;
import mw.client.gui.api.components.TextDisplay;
import mw.client.gui.api.interactive.TextField;
import mw.client.gui.api.layouts.GridLayout;
import mw.client.gui.api.layouts.HorizontalLayout;
import mw.client.gui.api.layouts.VerticalLayout;

public final class APITest {

	public static void main(String[] args) throws InterruptedException {
		/*MinuetoEventQueue queue = new MinuetoEventQueue();
		ResizableWindow frame = new ResizableWindow(300, 100, queue, "Login screen");
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
		
		Thread.sleep(1500);
		frame.resize(mainLayout.getWidth(), mainLayout.getHeight());
		mainLayout.drawOn(frame);
		frame.render();*/
		
		ResizableWindow frame = new ResizableWindow(300, 100, "BlockComponent test");
		frame.setVisible(true);
		
		VerticalLayout blockLayout = new VerticalLayout(10);
		
		GridLayout layout = new GridLayout(3, 3);
		
		layout.addComponent(new TextDisplay("1"), 0, 0);
		layout.addComponent(new TextDisplay("2"), 0, 1);
		layout.addComponent(new TextDisplay("3"), 0, 2);

		layout.addComponent(new TextDisplay("4"), 1, 0);
		layout.addComponent(new BlockComponent(100, 100, blockLayout), 1, 1);
		layout.addComponent(new TextDisplay("6"), 1, 2);

		layout.addComponent(new TextDisplay("7"), 2, 0);
		layout.addComponent(new TextDisplay("8"), 2, 1);
		layout.addComponent(new TextDisplay("9"), 2, 2);
		
		layout.drawOn(frame);
		frame.render();
		
		Thread.sleep(1500);
		frame.resize(layout.getWidth(), layout.getHeight());
		layout.drawOn(frame);
		frame.render();
		
		for (int i=0; i < 10; i++)
		{
			blockLayout.addComponent(new TextDisplay("block "+i));
			
			Thread.sleep(500);
			frame.resize(layout.getWidth(), layout.getHeight());
			layout.drawOn(frame);
			frame.render();
		}
		
		Thread.sleep(1000);
		blockLayout.removeAll();
		frame.resize(layout.getWidth(), layout.getHeight());
		layout.drawOn(frame);
		frame.render();
	}

}