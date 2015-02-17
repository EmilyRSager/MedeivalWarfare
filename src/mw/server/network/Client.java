/**
 * 
 */
package mw.server.network;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.concurrent.locks.Condition;

import mw.server.gamelogic.Message;

/**
 * @author Charlie Bloomfield
 * Feb 16, 2015
 * Modelled after code from "http://math.hws.edu/eck/cs124/javanotes5/source/BuddyChatServer.java"
 * 
 * Client class represents a single connection between the server and a client, ON THE SERVER. It has two threads,
 * one for reading data in from the Client's computer, and one thread for writing data to the Client's computer.
 */
public class Client {

	private static int aClientsCreated;  // How many clients have been created.
	private int aClientNumber;  // Clients are numbered 1, 2, ... as they are created.
	private volatile String aInfo;

	private ClientThread aClientThread;  // Writer thread, also sets up connection.
	private ReaderThread aReaderThread;
	private Socket aSocket;
	private volatile boolean aIsConnected;
	private volatile boolean aIsClosed;
	
	private String aMessageToSend;
	private Condition aMessageToSendAvailable;

	/**
	 * @param Socket through which the server communicates with this Client
	 * Construct a client, initialize the main client thread.
	 */
	public Client(Socket pSocket) {
		aClientsCreated++;
		aClientNumber = aClientsCreated; //each client's unique id

		aSocket = pSocket;
		aClientThread = new ClientThread();
		aClientThread.start();
		aMessageToSendAvailable = newCondition();
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

	/**
	 * Defines the main client thread, which is responsible for setting up
	 * the connection, starting the reader thread, and then writing all messages
	 * to the client.
	 */
	class ClientThread extends Thread {
		public void run() {
			try {
				BufferedReader lBufferedReader = 
						new BufferedReader(new InputStreamReader(aSocket.getInputStream()));
				
				BufferedWriter lBufferedWriter =
						new BufferedWriter(new OutputStreamWriter(aSocket.getOutputStream()));
				
				//greet Client
				lBufferedWriter.write("Welcome to Medieval Warfare server.");
				
				//main loop of ClientThread
				while(true){
					wait();
				}
				
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * Defines a thread that listens to messages coming in from clients. It closes when the
	 * client disconnects 
	 */
	class ReaderThread extends Thread {
		BufferedReader aBufferedReader;

		/**
		 * @param BufferedReader that reads data from the Socket that the client is connected to.
		 */
		ReaderThread(BufferedReader pBufferedReader) {
			aBufferedReader = pBufferedReader;
		}

		public void run() {
			try {
				while (true) {
					
					//read message in from Client
					String lMessageFromClient = aBufferedReader.readLine();
					
					/*
					 * Here, there needs to be a check of what kind of message this input is.
					 * This is just a test to see that messages work. But in production,
					 * we don't know what data the client has send, and so we don't know that
					 * it's a Message.
					 */
					sendMessage(Message.fromJson(lMessageFromClient));
				}
			}
			catch (Exception e) {
					System.out.println("Disconnecting from client " + aClientNumber + ".");
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
				SocketManager.getInstance().getSocket(lRecipientID);
		
		DataOutputStream lDataOutputStream;
		
		try {
			lDataOutputStream = new DataOutputStream(lDestinationSocket.getOutputStream());
			lDataOutputStream.writeUTF(pMessage.toJson());
			lDataOutputStream.close();
		} catch (IOException e) {
			System.out.println("Failed to send message: \n" + pMessage.toJson());
			e.printStackTrace();
		}
	}

	/**
	 * @return number of the client 
	 */
	public int getClientNumber() {
		return aClientNumber;
	}
}