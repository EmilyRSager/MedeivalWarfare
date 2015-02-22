/**
 * @author Charlie Bloomfield
 * Feb 20, 2015
 */

package mw.client.network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client extends Thread{
	private static final String SERVER_NAME = "localhost";
	private static final int PORT = 6666;

	private Socket aSocket;
	
	private Scanner reader = new Scanner(System.in);

	/**
	 * Constructor. Creates a Reader and Writer thread that communicate with the 
	 * @param none
	 */
	public Client(){
		try
		{
			aSocket = new Socket(SERVER_NAME, PORT);
			
			System.out.println("[Client] Connecting to " + SERVER_NAME + " on PORT " + PORT + ".");
			System.out.println("[Client] Just connected to " + aSocket.getRemoteSocketAddress());
			
			WriterThread lWriterThread = new WriterThread(
					new DataOutputStream(aSocket.getOutputStream()));
			lWriterThread.start();
			
			ReaderThread lReaderThread = new ReaderThread(
					new DataInputStream(aSocket.getInputStream()));
			lReaderThread.start();
				
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		Client lClient = new Client();
	}
}
