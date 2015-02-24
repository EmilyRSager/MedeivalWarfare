package mw.client.network;

import java.io.DataInputStream;
import java.util.concurrent.BlockingQueue;

import mw.shared.clientcommands.AbstractClientCommand;
import mw.utilities.ClientCommandSerializerAndDeserializer;

public class ReaderThread extends Thread {
	DataInputStream aDataInputStream;
	BlockingQueue<AbstractClientCommand> aServerMessageQueue;
	
	/**
	 * Constructor
	 * @param pDataInputStream
	 */
	public ReaderThread(DataInputStream pDataInputStream){
		aDataInputStream = pDataInputStream;
	}
	
	@Override
	public void run(){
		try{
			while(true){
				String lMessageFromServer = aDataInputStream.readUTF();
				System.out.println("[Client] Message received from server: " + lMessageFromServer);
				
				ClientCommandHandler.getInstance().handle(
						ClientCommandSerializerAndDeserializer.getInstance().deserialize(lMessageFromServer));
			}
		}
		catch(Exception e){
			System.out.println("[Client] Error sending message.");
			e.printStackTrace();
		}
	}
}