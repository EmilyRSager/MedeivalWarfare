
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
 * ServerClient class represents a single connection between the server and a client, ON THE SERVER. It has two threads,
 * one for reading data in from the Client's computer, and one thread for writing data to the Client's computer.
 * 
 */
public class ServerClient {

	private static int aNumClientsCreated;  // How many clients have been created.
	private int aClientNumber;  // Clients are numbered 1, 2, ... as they are created.

	private Socket aSocket;

	private volatile boolean aIsConnected;
	private volatile boolean aIsClosed;

	//Message queue stores Message to be sent
	private BlockingQueue<Message> aMessageQueue;

	/**
	 * @param Socket through which the server communicates with this Client
	 * Construct a client, initialize the main client thread.
	 */
	public ServerClient(Socket pSocket) {
		aClientNumber = aNumClientsCreated; //each client's unique id
		aNumClientsCreated++;

		aMessageQueue = new LinkedBlockingQueue<Message>();
		aSocket = pSocket;

		SocketManager.getInstance().putSocket(aClientNumber, aSocket);

		ClientThread lClientThread = new ClientThread();
		lClientThread.start();
	}

	/**
	 * Defines the main client thread, which is responsible for setting up
	 * the connection, starting the reader thread, and then writing all messages
	 * to the client.
	 */
	class ClientThread extends Thread {
		DataOutputStream aDataOutputStream;

		public ClientThread(){
			try {
				aDataOutputStream = new DataOutputStream(aSocket.getOutputStream());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		public void run() {
			ReaderThread lReaderThread = new ReaderThread();
			lReaderThread.start();

			//main loop of ClientThread
			while(true){
				try {
					Message lMessage = aMessageQueue.take();
					sendMessage(lMessage);

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

		/**
		 * @param BufferedReader that reads data from the Socket that the client is connected to.
		 */
		ReaderThread() {
			try {
				aDataInputStream = new DataInputStream(aSocket.getInputStream());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		public void run() {
			try {
				while (true) {

					//read message in from Client
					String lMessageFromClient = aDataInputStream.readUTF();
					/**
					 * TODO readUTF does not succeed. Need to use something different than
					 * DataInputStream.readUTF(). 
					 */
					
					System.out.println(lMessageFromClient);
					
					aMessageQueue.put(Message.fromJson(lMessageFromClient));
				}
			}
			catch (Exception e) {
				System.out.println("Disconnecting from client " + aClientNumber + ".");
				e.printStackTrace();
			}
			finally {
				close();
			}
		}
	}

	/**
	 * Sends a message to the recipient specified by pMessage.aRecipientID
	 * @param pMessage
	 */
	private void sendMessage(Message pMessage){
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
	 * @return the Client's ID number
	 */
	public int getClientNumber() {
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

	/**
	 * Schedule a message to be sent by the writer thread.  This method does
	 * NOT actually send the message, so it does not block.  (Note: NO line
	 * feed is added to the message!)
	 */
	public synchronized void sendMessage(String pMessage) {
		//TODO build message to be sent to client computer
		notify();  // Wake up writer thread so it can send the message.
	}

	/**
	 * Schedule the client list to be sent by the writer thread.  This method
	 * does not actually send the list, so it does not block.
	 */
	public synchronized void sendClientList(ClientList pClients) {
		//TODO build a message to be sent from input ClientLists
		notify(); // Wake up writer thread so it can send the message.
	}
}