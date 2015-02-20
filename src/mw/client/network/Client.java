package mw.client.network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client extends Thread{
	private static final String serverName = "localhost";
	private static final int port = 6666;

	private Socket aClientSocket;
	
	private Scanner reader = new Scanner(System.in);

	public Client(){
		try
		{
			aClientSocket = new Socket(serverName, port);
			System.out.println("[Client] Connecting to " + serverName
					+ " on port " + port + ".");
			
			
			System.out.println("[Client] Just connected to "
					+ aClientSocket.getRemoteSocketAddress());
			
			WriterThread lWriterThread = new WriterThread();
			lWriterThread.start();
			
			ReaderThread lReaderThread = new ReaderThread();
			lReaderThread.start();
			
			
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	class WriterThread extends Thread{
		
		DataOutputStream aDataOutputStream;	
		public void run(){
			
			try{
				aDataOutputStream = new DataOutputStream(aClientSocket.getOutputStream());
				
				while(true){
					String lMessageToSend = reader.next();
					aDataOutputStream.writeUTF(lMessageToSend);
					
				}
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
	private class ReaderThread extends Thread{
		DataInputStream aDataInputStream;
		
		
		public void run(){
			
			try{
				aDataInputStream = new DataInputStream(aClientSocket.getInputStream());
			
				while(true){
					
					String lMessageBeingRead = aDataInputStream.readUTF();
					System.out.println(lMessageBeingRead);
				}
			}
			catch(Exception e){
				System.out.println("in the catch block");
				e.printStackTrace();
			}
			
		}
	}
	public static void main(String[] args) {
		Client lClient = new Client();
	}
}
