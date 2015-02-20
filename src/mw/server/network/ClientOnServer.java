
package mw.server.network;

import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

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
	private int aClientNumber;  // Clients are numbered 1, 2, ... as they are created.

	private Socket aSocket;

	private volatile boolean aIsConnected;
	private volatile boolean aIsClosed;

	//String queue stores Strings for testing
	private BlockingQueue<String> aBlockingQueue;

	/**
	 * @param psSocket through which the server communicates with this Client
	 * Construct a client, initialize the main client thread.
	 */
	public ClientOnServer(Socket pSocket) {
		aClientNumber = aNumClientsCreated; //each client's unique id
		aNumClientsCreated++;

		aBlockingQueue = new LinkedBlockingQueue<String>();
		aSocket = pSocket;

		//add 
		SocketManager.getInstance().putSocket(aClientNumber, aSocket);

		ClientWriterThread lClientWriterThread = new ClientWriterThread(aSocket, aBlockingQueue);
		lClientWriterThread.start();
		
		ClientReaderThread lClientReaderThread = new ClientReaderThread(aSocket, aBlockingQueue);
		lClientReaderThread.start();
	}

	/**
	 * @return the Client's ID number
	 */
	public synchronized int getClientNumber() {
		return aClientNumber;
	}

	/**
	 * Called by ClientList when the server is shutting down;
	 * Closes this client's socket (which terminates the reader thread)
	 * and wakes up the writer thread so it can terminate.
	 */
	public synchronized void shutDown() {
		if (! aIsClosed) {
			aIsClosed = true;
			try {
				aSocket.close();
			}
			catch (Exception e) {
			}
			synchronized(this) {
				notify();  // Notifies writer thread.
			}
		}
	}

	/**
	 * Called when either the reader or writer thread shuts down.  This
	 * can happen because of an error or because the connection is aIsClosed
	 * from the other side.  (It will also be called during server shutdown.)
	 * If connection is already aIsClosed, nothing is done.
	 */
	public synchronized void close() {
		if (!aIsClosed) {
			aIsClosed = true;
			try {
				aSocket.close();
			}
			catch (Exception e) {
			}
			notify();
		}
	}
}