/**
 * @author Charlie Bloomfield
 * Feb 20, 2015
 */

package mw.client.network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import mw.shared.servercommands.AbstractServerCommand;

public class ServerChannel extends Thread{
<<<<<<< HEAD
	private static final String SERVER_NAME = "localhost";
=======
	private static final String SERVER_NAME = "132.206.54.88";
>>>>>>> 6a4e1442138cec220db35bfa222923bee16f84d0
	private static final int PORT = 6666;

	private Socket aSocket;
	
	private WriterThread aWriterThread;
	private ReaderThread aReaderThread; //no need for reference to reader thread

	/**
	 * Constructor. Initializes two threads to read data from and write data to the server.
	 * @param none
	 */
	public ServerChannel(){
		//TODO split up class construction and socket initialization
		try
		{
			aSocket = new Socket(SERVER_NAME, PORT);
			
			System.out.println("[Client] Connecting to " + SERVER_NAME + " on PORT " + PORT + ".");
			System.out.println("[Client] Just connected to " + aSocket.getRemoteSocketAddress());
			
			aWriterThread = new WriterThread(
					new DataOutputStream(aSocket.getOutputStream()));
			aWriterThread.start();
			
			
			aReaderThread = new ReaderThread(
					new DataInputStream(aSocket.getInputStream()));
			aReaderThread.start();
				
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public void sendCommand(AbstractServerCommand pServerCommand){
		aWriterThread.sendCommand(pServerCommand);
	}
}
