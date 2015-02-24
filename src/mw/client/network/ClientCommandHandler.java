package mw.client.network;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import mw.shared.clientcommands.AbstractClientCommand;

public class ClientCommandHandler {
	
	private BlockingQueue<AbstractClientCommand> aClientCommandQueue;
	private static ClientCommandHandler aClientCommandHandler;
	
	private ClientCommandHandler(){
		aClientCommandQueue = new LinkedBlockingQueue<AbstractClientCommand>();
		new ClientCommandHandlerThread().start();
	}
	
	public static ClientCommandHandler getInstance(){
		if (aClientCommandHandler==null) {
			aClientCommandHandler = new ClientCommandHandler();
		}
		
		return aClientCommandHandler;	
	}
	
	public void handle(AbstractClientCommand pClientCommand){
		try {
			aClientCommandQueue.put(pClientCommand);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	class ClientCommandHandlerThread extends Thread{
		
		@Override
		public void run(){
			while(true){
				try {
					AbstractClientCommand lClientCommand = 
							aClientCommandQueue.take();
					
					lClientCommand.execute();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}
