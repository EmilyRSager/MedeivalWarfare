package mw.client.app.test;
/**
 * @(#)RectangleDemo.java        1.00 15/09/2004
 *
 * Minueto - The Game Development Framework 
 * Copyright (c) 2004 McGill University
 * 3480 University Street, Montreal, Quebec H3A 2A7
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 **/
 
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Random;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.minueto.MinuetoColor;
import org.minueto.MinuetoEventQueue;
import org.minueto.MinuetoStopWatch;
import org.minueto.handlers.MinuetoFocusHandler;
import org.minueto.handlers.MinuetoKeyboard;
import org.minueto.handlers.MinuetoKeyboardHandler;
import org.minueto.handlers.MinuetoMouseHandler;
import org.minueto.image.MinuetoFont;
import org.minueto.image.MinuetoImage;
import org.minueto.image.MinuetoText;
import org.minueto.window.MinuetoPanel;

/**
 * Example of running Minueto inside a Swing Frame utilizing the new MinuetoPanel.
 * 
 * @author Michael A. Hawker
 * @since 1.1
 * @version 1.0
 * @see org.minueto.window.MinuetoPanel
 **/
public class MinuetoPanelDemo implements MinuetoKeyboardHandler, MinuetoMouseHandler, MinuetoFocusHandler {
	
	private JFrame frameWindow;
	private MinuetoPanel mwiPanel;
	private MinuetoEventQueue meqQueue;
	private JButton exitButton;
	private JTextField txtField;
	private boolean isRunning = true;
	private String myText = "My Text Here";
	
	public MinuetoPanelDemo() {
		
		frameWindow = new JFrame("Minueto Test Window");
		frameWindow.setLayout(new FlowLayout());
		
		// Register Window Listener to notify render loop that window has closed
		frameWindow.addWindowListener(new WindowListener() {
			public void windowActivated(WindowEvent arg0) {}
			public void windowClosed(WindowEvent arg0) {}

			public void windowClosing(WindowEvent arg0) {
				isRunning = false;				
			}

			public void windowDeactivated(WindowEvent arg0) {}
			public void windowDeiconified(WindowEvent arg0) {}
			public void windowIconified(WindowEvent arg0) {}
			public void windowOpened(WindowEvent arg0) {}
		});
		
		// Create another JPanel to align controls vertically
		JPanel align = new JPanel();
		align.setLayout(new BoxLayout(align, BoxLayout.PAGE_AXIS));
		
		// Create a text field, text will be displayed in MinuetoPanel
		txtField = new JTextField(myText);
		txtField.setSize(200, 50);
		txtField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				synchronized (myText) {
					myText = txtField.getText();
				}
			}
		});
		align.add(txtField);
		align.add(Box.createRigidArea(new Dimension(0, 8)));
				
		// Create Button to Exit Program
		exitButton = new JButton("Exit");
        // Make an Action
        exitButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
            	// Let's just exit
            	System.exit(0);
            }
        });
		align.add(exitButton);
		
		frameWindow.add(align);
		
		// Create a 640 by 480 window.
		mwiPanel = new MinuetoPanel(640, 480);
		
		// Create and register event queue
		meqQueue = new MinuetoEventQueue();
		/* 
		 * Note that you need to click in the MinuetoPanel to give it focus
		 * for it to receive keyboard input just like any other Swing component.
		 * 
		 * i.e. if you click in the textbox to type, the input will only be
		 * sent to the textbox and not the MinuetoPanel.  If you want input to
		 * resume to the MinuetoPanel, you need to click in it first.
		 */
		mwiPanel.registerKeyboardHandler(this, meqQueue);
		mwiPanel.registerMouseHandler(this, meqQueue);
		mwiPanel.registerFocusHandler(this, meqQueue);
		
		// Add to frame just like JPanel!
		frameWindow.add(mwiPanel);
		
		frameWindow.pack();
	}
	
	public void setVisible(boolean value) {
		System.out.println("Started settingVisible");
		frameWindow.setVisible(value);
		frameWindow.requestFocus();
		
		// make our panel visible too
		mwiPanel.setVisible(value);
		System.out.println("SetVisible done");
	}
	
	public void run() {
		MinuetoFont mfoArial16;				// Font used to write framerate.
		MinuetoImage mitFramerate;			// Text use to display framerate.
		MinuetoImage mitText;
		MinuetoStopWatch mswWatch;			// Timer used to calculate framerate.
		MinuetoColor mcoColor[] = new MinuetoColor[5];	// Array of color.
		
		Random ranNumGenerator;				// Our random number generator.
		
		double dFrameRate;					// Current framerate.
		int iNumberLines;					// Current number of lines drawn on screen.
		int iFrameCount;					// Used to calculate the framerate
		
		int i,j,k,x,y,c;					// Temporary integers.
		
		
		// Init random number generator.
		ranNumGenerator = new Random();		
		
		// Init the timer.
		mswWatch = new MinuetoStopWatch();
		
		// Init the font.
		mfoArial16 = new MinuetoFont("Arial",16,false, false);
		
		// Prepare the color array.
		mcoColor[0] = MinuetoColor.BLACK;
		mcoColor[1] = MinuetoColor.RED;
		mcoColor[2] = MinuetoColor.GREEN;
		mcoColor[3] = MinuetoColor.BLUE;
		mcoColor[4] = MinuetoColor.WHITE;
		
		// Init the framerate text (so it's not null on the first frames).		
		mitFramerate = new MinuetoText("FPS: 0",mfoArial16,MinuetoColor.BLUE);
		mitText = new MinuetoText(myText,mfoArial16,MinuetoColor.WHITE);
		
		// Start the timer.
		mswWatch.start();
		
		// Get reday to count the FPS		
		iFrameCount = 0;
		
		// At first, we draw no lines
		iNumberLines = 0;
					
		// Game/Rendering loop	
		while(isRunning) {
			
			// Handle all the events in the event queue.
			while (meqQueue.hasNext()) {
				meqQueue.handle();
			}
		
			mwiPanel.clear(MinuetoColor.BLACK);
			
			// For every lines we are supposed to draw:
			for(i = 0; i < iNumberLines; i++) {
				j = ranNumGenerator.nextInt(640); //Starting X of the line.
				k = ranNumGenerator.nextInt(480); //Starting Y of the line.
				x = ranNumGenerator.nextInt(640); //Ending X of the line.
				y = ranNumGenerator.nextInt(480); //Ending Y of the line.
				c = ranNumGenerator.nextInt(5);	// Random color of the line.
				mwiPanel.drawLine(mcoColor[c], j, k, x, y); // Draw the line.
			}
			
			// Count this frame
			iFrameCount++;
			// We update the framerate every thread frames.
			if (iFrameCount == 20) {
				mswWatch.stop();
				
				// Using the timer, we calculate how much time was needed to
				// draw the 20 frames. This gives us our framterate.
				dFrameRate =  (double)20000/(double)mswWatch.getTime();
				// If our FPS is > 31, then we add some lines.
				if (dFrameRate > 31.0) iNumberLines = iNumberLines + (int)Math.round(Math.abs(dFrameRate - 30.0));
				// If our FPS is < 29, then we remove some lines.
				if (dFrameRate < 29.0) iNumberLines--;
				
				//We build the image that will display the framerate and the line count.
				mitFramerate = new MinuetoText("FPS: " + dFrameRate + " Lines: " + iNumberLines ,mfoArial16,MinuetoColor.BLUE);
				
				synchronized (myText) {
					mitText = new MinuetoText(myText,mfoArial16,MinuetoColor.WHITE);
				}
				// And we start counting the frame agains ...
				iFrameCount = 0;
				// ...after having properly reset the counter.
				mswWatch.reset();
				mswWatch.start();

			}
			
			// We display the framerate and the line count
			mwiPanel.draw(mitFramerate, 0, 0);
			mwiPanel.draw(mitText, mwiPanel.getWidth()/2-mitText.getWidth()/2, mwiPanel.getHeight()/2-mitText.getHeight()/2);
			
			// Render all graphics in the back buffer.			
			mwiPanel.render();
		}
		
		mwiPanel.close();
		frameWindow.dispose();
		frameWindow = null;
	}
	
	/**
	 * We need this to make our demo runnable from the command line.
	 **/
	public static void main(String[] args) {
	
		MinuetoPanelDemo main = new MinuetoPanelDemo();
		main.setVisible(true);
		main.run();
	}

	public void handleKeyPress(int iValue) {
		System.out.println("Keyboard key " + iValue + " was pressed.");
		
		if (iValue == MinuetoKeyboard.KEY_Q) { System.exit(0); }
	}

	public void handleKeyRelease(int iValue) {
		System.out.println("Keyboard key " + iValue + " was released.");		
	}

	public void handleKeyType(char cKeyChar) {
		
	}

	public void handleMouseMove(int iX, int iY) {	
		
	}

	public void handleMousePress(int iX, int iY, int iButton) {
		System.out.println("Mouse click on button " + iButton + " detected at " + iX + "," + iY);		
	}

	public void handleMouseRelease(int iX, int iY, int iButton) {
		System.out.println("Mouse release on button " + iButton + " detected at " + iX + "," + iY);	
	}

	public void handleGetFocus() {
		System.out.println("Window got focus!");
	}

	public void handleLostFocus() {
		System.out.println("Window lost focus!");		
	}

}