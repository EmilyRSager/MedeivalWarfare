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

	private Socket aClientSocket;
	
	private Scanner reader = new Scanner(System.in);

	/**
	 * Constructor. Creates a Reader and Writer thread that communicate with the 
	 * @param none
	 */
	public Client(){
		try
		{
			aClientSocket = new Socket(SERVER_NAME, PORT);
			System.out.println("[Client] Connecting to " + SERVER_NAME
					+ " on PORT " + PORT + ".");
			
			System.out.println("[Client] Just connected to "
					+ aClientSocket.getRemoteSocketAddress());
			
			WriterThread lWriterThread = new WriterThread();
			lWriterThread.start();
			
			ReaderThread lReaderThread = new ReaderThread();
			lReaderThread.start();
				
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	class WriterThread extends Thread{
		DataOutputStream aDataOutputStream;	
		public void run(){
			try{
				while(true){
					System.out.println("[Client] Enter message to send.");
					String lMessageToSend = reader.next();
					
					aDataOutputStream.writeUTF(lMessageToSend);
					sleep(10000);
				}
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
	private class ReaderThread extends Thread{
		DataInputStream aDataInputStream;
		
		public void run(){
			
			try{
				aDataInputStream = new DataInputStream(aClientSocket.getInputStream());
			
				while(true){
					String lMessageBeingRead = aDataInputStream.readUTF();
					System.out.println("[Client] Message received: " + lMessageBeingRead);
				}
			}
			catch(Exception e){
				System.out.println("[Client] Error sending message.");
				e.printStackTrace();
			}
			
		}
	}
	public static void main(String[] args) {
		Client lClient = new Client();
	}
}
