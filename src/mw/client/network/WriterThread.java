package mw.client.network;

import java.io.DataOutputStream;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import mw.shared.servercommands.AbstractServerCommand;
import mw.utilities.ServerCommandSerializerAndDeserializer;

public class WriterThread extends Thread {
	DataOutputStream aDataOutputStream;	
	BlockingQueue<AbstractServerCommand> aServerCommandQueue;
	
	public WriterThread(DataOutputStream pDataOutputStream) {
		
		aDataOutputStream = pDataOutputStream;
		aServerCommandQueue = new LinkedBlockingQueue<AbstractServerCommand>();
		
	}
	
	public void addMessageToQueue(AbstractServerCommand pServerCommand){
		
		try {
			aServerCommandQueue.put(pServerCommand);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
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
