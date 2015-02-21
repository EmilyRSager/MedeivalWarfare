/**
 * @author Charlie Bloomfield
 * Feb 20, 2015
 * 
 */
package mw.server.network;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import mw.shared.servermessages.AbstractServerMessage;

/** 
 * @singleton
 * This class handles AbstractGameMessages. It does so by creating a thread which waits for 
 * AbstractServerMessages to become available in a BlockingQueue. It calls the verify method on each
 * AbstractServerMessage, and if valid, calls execute on each message.
 * 
 * TODO
 * If the AbstractServerMessage is not valid, it must initiate forwarding a message to the
 * client who sent the AbstractServerMessage that informs the User the reason the AbstractServerMessage
 * is invalid.
 */
public class ServerMessageHandler {
	
	private static ServerMessageHandler aServerMessageHandler;
	private BlockingQueue<ServerMessageWrapper> aServerMessageQueue;
	
	/**
	 * Constructor. Initiates a ServerMessageHandlerThread, which exists for the entire execution
	 * of the server program.
	 * @param none
	 */
	private ServerMessageHandler(){
		aServerMessageQueue = new LinkedBlockingQueue<ServerMessageWrapper>();
		ServerMessageHandlerThread lServerMessageHandlerThread = new ServerMessageHandlerThread();
		lServerMessageHandlerThread.start();
	}
	
	/**
	 * Singleton implementation
	 * @param none
	 * @return the static instance of ServerMessageHandler
	 */
	public static ServerMessageHandler getInstance(){
		if(aServerMessageHandler == null){
			aServerMessageHandler = new ServerMessageHandler();
		}
		
		return aServerMessageHandler;
	}
	
	/**
	 * Enqueues pServerMessage to be serviced when the AbstractServerMessageThread
	 * is available to service it.
	 * @param pServerMessage, the server message to be handled
	 * @param pClientID, the identification number of the requesting Client.
	 * @return none
	 */
	public synchronized void handle(AbstractServerMessage pServerMessage, int pClientID){
		try {
			
			aServerMessageQueue.put(
					new ServerMessageWrapper(pServerMessage, pClientID));
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public synchronized void testHandle(String pTestMessage){
		ClientManager.getInstance().get(0).testSendString(pTestMessage); //TEST! Send message back to client 0 through pipeline
	}
	
	/*
	 * Nested Thread class ServerMessageHandlerThread handles all ServerMessages, for all games.
	 */
	class ServerMessageHandlerThread extends Thread{
		public void run(){
			while(true){
				try {
					ServerMessageWrapper lServerMessageWrapper = aServerMessageQueue.take();
					AbstractServerMessage lServerMessage = lServerMessageWrapper.getServerMessage();
					int lClientID = lServerMessageWrapper.getClientID();
					
					if(! lServerMessage.isValid(lClientID)){
						//TODO how to deal with recovery?
					}
					
					else{
						lServerMessage.execute(lClientID);
					}
					
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	/*
	 * Nested class maintains the ClientID attribute.
	 */
	class ServerMessageWrapper{
		AbstractServerMessage aAbstractServerMessage;
		int aClientID;
		
		ServerMessageWrapper(AbstractServerMessage pServerMessage, int pClientID) {
			aAbstractServerMessage = pServerMessage;
			aClientID = pClientID;
		}
		
		public AbstractServerMessage getServerMessage(){
			return aAbstractServerMessage;
		}
		
		public int getClientID(){
			return aClientID;
		}
	}
}
