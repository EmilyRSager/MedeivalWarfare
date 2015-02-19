package mw.client.network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

import mw.server.gamelogic.Message;

public class Client extends Thread{
	private static final String serverName = "localhost";
	private static final int port = 6666;

	private Socket aClientSocket;
	
	private Scanner reader = new Scanner(System.in);

	public Client(){
		try
		{
			aClientSocket = new Socket(serverName, port);
			System.out.println("Connecting to " + serverName
					+ " on port " + port + ".");
			
			
			System.out.println("Just connected to "
					+ aClientSocket.getRemoteSocketAddress());
			
			
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
				System.out.println("in the catch block");
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
	
	
//	public void sendMessage(Message pMessage){
//		String s = pMessage.toJson();
//		try {
//			aDataOutputStream.writeUTF(s);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}


//	public void readMessage(){
//		try {
//			Message m = Message.fromJson(aDataInputStream.readUTF());
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}

//	public void sendString(String pString){
//		System.out.println("Sending \"" + pString + "\" to server.");
//		try {
//			aDataOutputStream.writeUTF(pString);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}

//	public void readString(){
//		try {
//			System.out.println("Reading string from server.");
//			System.out.println(aDataInputStream.readUTF());
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
}
