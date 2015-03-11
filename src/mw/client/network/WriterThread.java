/**
 * @author Charlie Bloomfield, Abhishek Gupta
 * Feb 23, 2015
 */

package mw.client.network;

import java.io.DataOutputStream;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import mw.serialization.ServerCommandSerializerAndDeserializer;
import mw.shared.servercommands.AbstractServerCommand;

public class WriterThread extends Thread {
	DataOutputStream aDataOutputStream;	
	BlockingQueue<AbstractServerCommand> aServerCommandQueue;
	
	/**
	 * Constructor.
	 * @param pDataOutputStream
	 */
	public WriterThread(DataOutputStream pDataOutputStream) {
		aDataOutputStream = pDataOutputStream;
		aServerCommandQueue = new LinkedBlockingQueue<AbstractServerCommand>();
	}
	
	/**
	 * Enqueues a command to be sent when this thread is available.
	 * @param pServerCommand
	 */
	public void sendCommand(AbstractServerCommand pServerCommand){
		try {
			aServerCommandQueue.put(pServerCommand);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void run(){
		try{
			while(true){
				AbstractServerCommand lServerCommand = aServerCommandQueue.take();
				
				//System.out.println("[Client] Enter message to send.");
				//String lMessageToSend = reader.next();
								
				aDataOutputStream.writeUTF(
						ServerCommandSerializerAndDeserializer.getInstance().serialize(lServerCommand));
				//sleep(10000);
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
}
