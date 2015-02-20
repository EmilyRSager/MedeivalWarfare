
package mw.server.network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import mw.server.gamelogic.Message;

/**
 * @author Charlie Bloomfield
 * Feb 16, 2015
 * Modelled after code from "http://math.hws.edu/eck/cs124/javanotes5/source/BuddyChatServer.java"
 * 
 * ClientRepOnServer class represents a single connection between the server and a client, ON THE SERVER. It has two threads,
 * one for reading data in from the Client's computer, and one thread for writing data to the Client's computer.
 * 
 */
public class ClientRepOnServer {

	private static int aNumClientsCreated;  // How many clients have been created.
	private int aClientNumber;  // Clients are numbered 1, 2, ... as they are created.

	private Socket aSocket;

	private volatile boolean aIsConnected;
	private volatile boolean aIsClosed;

	//Message queue stores Message to be sent
	private BlockingQueue<Message> aMessageQueue;

	//String queue stores Strings for testing
	private BlockingQueue<String> aStringQueue;

	/**
	 * @param psSocket through which the server communicates with this Client
	 * Construct a client, initialize the main client thread.
	 */
	public ClientRepOnServer(Socket pSocket) {
		aClientNumber = aNumClientsCreated; //each client's unique id
		aNumClientsCreated++;

		aMessageQueue = new LinkedBlockingQueue<Message>();
		aStringQueue = new LinkedBlockingQueue<String>();
		aSocket = pSocket;

		SocketManager.getInstance().putSocket(aClientNumber, aSocket);

		WriterThread lWriterThread = new WriterThread();
		lWriterThread.start();
	}

	/**
	 * Defines the main client thread, which is responsible for setting up
	 * the connection, starting the reader thread, and then writing all messages
	 * to the client.
	 */
	class WriterThread extends Thread {
		DataOutputStream aDataOutputStream;

		public void run() {
			ReaderThread lReaderThread = new ReaderThread();
			lReaderThread.start();

			try {
				//open DataOutputStream for writing back to client
				aDataOutputStream = new DataOutputStream(aSocket.getOutputStream());
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			//main loop of WriterThread
			while(true){
				try {
					String lString = aStringQueue.take();
					echoString(lString);
					
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * Defines a thread that listens to messages coming in from the client. It closes when the
	 * client disconnects 
	 */
	class ReaderThread extends Thread {
		DataInputStream aDataInputStream;

		public void run() {
			try {
				aDataInputStream = new DataInputStream(aSocket.getInputStream());
				while (true) {
					
					System.out.println("Listening to client " + aClientNumber + ".");

					//read message in from Client
					String lMessageFromClient = aDataInputStream.readUTF();
					System.out.println("Message from client \"" + lMessageFromClient + "\".");
					//echoString(lMessageFromClient);
					//aDataInputStream.close();

					//System.out.println("read on server" + lMessageFromClient);
					aStringQueue.put(lMessageFromClient);

					//aMessageQueue.put(Message.fromJson(lMessageFromClient));
				}
			}
			catch (Exception e) {
				System.out.println("Disconnecting from client " + aClientNumber + ".");
				e.printStackTrace();
			}
		}
	}

	/**
	 * Sends a message to the recipient specified by pMessage.aRecipientID
	 * @param pMessage
	 */
	private synchronized void sendMessage(Message pMessage){
		int lRecipientID = pMessage.getRecipientID(); //extract recipientID from the message

		Socket lDestinationSocket = 
				SocketManager.getInstance().getSocket(lRecipientID); //get socket associated with the ID

		try {
			/* This implementation sends data directly from the reader thread.
			 * This should not be the case in the future.
			 */
			DataOutputStream lDataOutputStream = 
					new DataOutputStream(lDestinationSocket.getOutputStream());

			lDataOutputStream.writeUTF(pMessage.toJson());
			lDataOutputStream.close();
		} catch (IOException e) {
			System.out.println("Failed to send message: \n" + pMessage.toJson());
			e.printStackTrace();
		}
	}

	/**
	 * @param pString
	 * Writes pString back to the Socket associated with the client communicating
	 * with this thread.
	 */
	private synchronized void echoString(String pString){
		try {
			System.out.println("Echoing string \"" + pString + "\" to client.");
			DataOutputStream lDataOutputStream =
					new DataOutputStream(aSocket.getOutputStream());
			
			lDataOutputStream.writeUTF(pString);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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