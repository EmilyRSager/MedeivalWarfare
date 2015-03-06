/**
 * @author Charlie Bloomfield
 * Feb 16, 2015
 */

package mw.server.network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import mw.shared.clientcommands.AbstractClientCommand;

/**
 * ClientOnServer class represents a single connection between the server and a client, ON THE SERVER. It has two threads,
 * one for reading data in from the Client's computer, and one thread for writing data to the Client's computer.
 */
public class ClientChannel extends Thread{

	private static int aNumClientsCreated;  // How many clients have been created.
	private int aClientID;  // Clients are numbered 1, 2, ... as they are created.

	private Socket aSocket;
	
	private ReaderThread aReaderThread;
	private WriterThread aWriterThread;

	/**
	 * @param psSocket through which the server communicates with this Client
	 * Construct a client, initialize the main client thread.
	 */
	public ClientChannel(Socket pSocket) {
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
	 * Forwards pClientMessage to the writer thread dedicated to writing messages
	 * over the network.
	 * @param AbstractClientMessage to send to this Client
	 * @return void
	 */
	public void sendCommand(AbstractClientCommand pClientCommand){
		aWriterThread.sendCommand(pClientCommand);
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
	public synchronized void shutDown(){
		aReaderThread.shutDown();;
		aWriterThread.shutDown();
	}
}