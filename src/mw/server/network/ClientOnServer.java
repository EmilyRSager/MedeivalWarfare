
package mw.server.network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;

import mw.shared.AbstractClientMessage;
import mw.shared.AbstractServerMessage;

/**
 * @author Charlie Bloomfield
 * Feb 16, 2015
 * Modelled after code from "http://math.hws.edu/eck/cs124/javanotes5/source/BuddyChatServer.java"
 * 
 * ClientOnServer class represents a single connection between the server and a client, ON THE SERVER. It has two threads,
 * one for reading data in from the Client's computer, and one thread for writing data to the Client's computer.
 * 
 */
public class ClientOnServer {

	private static int aNumClientsCreated;  // How many clients have been created.
	private int aClientID;  // Clients are numbered 1, 2, ... as they are created.

	private Socket aSocket;
	
	private ReaderThread aReaderThread;
	private WriterThread aWriterThread;

	private volatile boolean aIsConnected;
	private volatile boolean aIsClosed;

	//String queue stores Strings for testing
	private BlockingQueue<AbstractServerMessage> aGameMessageQueue;

	/**
	 * @param psSocket through which the server communicates with this Client
	 * Construct a client, initialize the main client thread.
	 */
	public ClientOnServer(Socket pSocket) {
		aClientID = aNumClientsCreated; //each client's unique id
		aNumClientsCreated++;
		aSocket = pSocket;		
		
		DataOutputStream lDataOutputStream;
		DataInputStream lDataInputStream;
		
		//TODO this is not precise enough exception handling, need to refactor
		try {
			lDataOutputStream = new DataOutputStream(aSocket.getOutputStream());
			lDataInputStream = new DataInputStream(aSocket.getInputStream());
			
			aWriterThread = new WriterThread(lDataOutputStream);
			aWriterThread.start();
			
			aReaderThread = new ReaderThread(lDataInputStream, aClientID);
			aReaderThread.start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 *@param
	 *@return void
	 */
	public void sendMessage(AbstractClientMessage pClientMessage){
		aWriterThread.sendMessage(pClientMessage);
	}

	/**
	 * @param none
	 * @return the Client's ID number
	 */
	public int getClientID() {
		return aClientID;
	}
	
	/**
	 * @param none
	 * @return void
	 * Closes both reader and writer threads, and does all other necessary clean up.
	 */
	public synchronized void close(){
		
	}
}