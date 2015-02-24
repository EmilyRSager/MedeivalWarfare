/**
 * @author Charlie Bloomfield
 * Feb 20, 2015
 * 
 */
package mw.server.network;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import mw.shared.servercommands.AbstractServerCommand;

/** 
 * @singleton
 * This class handles AbstractGameMessages. It does so by creating a thread which waits for 
 * AbstractServerCommands to become available in a BlockingQueue. It calls the verify method on each
 * AbstractServerCommand, and if valid, calls execute on each message.
 * 
 * TODO
 * If the AbstractServerCommand is not valid, it must initiate forwarding a message to the
 * client who sent the AbstractServerCommand that informs the User the reason the AbstractServerCommand
 * is invalid.
 */
public class ServerCommandHandler {
	
	private static ServerCommandHandler aServerCommandHandler;
	private BlockingQueue<ServerCommandWrapper> aServerCommandQueue;
	
	/**
	 * Constructor. Initiates a ServerCommandHandlerThread, which exists for the entire execution
	 * of the server program.
	 */
	private ServerCommandHandler(){
		aServerCommandQueue = new LinkedBlockingQueue<ServerCommandWrapper>();
		ServerCommandHandlerThread lServerCommandHandlerThread = new ServerCommandHandlerThread();
		lServerCommandHandlerThread.start();
	}
	
	/**
	 * Singleton implementation
	 * @return the static instance of ServerCommandHandler
	 */
	public static ServerCommandHandler getInstance(){
		if(aServerCommandHandler == null){
			aServerCommandHandler = new ServerCommandHandler();
		}
		
		return aServerCommandHandler;
	}
	
	/**
	 * Enqueues pServerCommand to be serviced when the ServerCommandHandlerThread
	 * is available to service it.
	 * @param pServerCommand, the server message to be handled
	 * @param pClientID, the identification number of the requesting Client.
	 */
	public synchronized void handle(AbstractServerCommand pServerCommand, int pClientID){
		try {
			
			aServerCommandQueue.put(
					new ServerCommandWrapper(pServerCommand, pClientID));
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public synchronized void testHandle(String pTestMessage){
		ClientChannelManager.getInstance().getChannel(0).testSendString(pTestMessage); //TEST! Send message back to client 0 through pipeline
	}
	
	/*
	 * Nested Thread class ServerCommandHandlerThread handles all ServerCommands, for all games.
	 */
	class ServerCommandHandlerThread extends Thread{
		public void run(){
			while(true){
				try {
					ServerCommandWrapper lServerCommandWrapper = aServerCommandQueue.take();
					AbstractServerCommand lServerCommand = lServerCommandWrapper.getServerCommand();
					int lClientID = lServerCommandWrapper.getClientID();
					
					if(! lServerCommand.isValid(lClientID)){
						//TODO how to deal with recovery?
					}
					
					else{
						lServerCommand.execute(lClientID);
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
	class ServerCommandWrapper{
		AbstractServerCommand aAbstractServerCommand;
		int aClientID;
		
		ServerCommandWrapper(AbstractServerCommand pServerCommand, int pClientID) {
			aAbstractServerCommand = pServerCommand;
			aClientID = pClientID;
		}
		
		public AbstractServerCommand getServerCommand(){
			return aAbstractServerCommand;
		}
		
		public int getClientID(){
			return aClientID;
		}
	}
}
