/**
 * @author Charlie Bloomfield
 * Feb 20, 2015
 */

package mw.client.network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.HashSet;
import java.util.Scanner;

import com.google.gson.Gson;

import mw.shared.TestServerMessage;

public class Client extends Thread{
	private static final String serverName = "132.206.54.82";
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
				HashSet<Integer> set = new HashSet<Integer>();
				set.add(0);
				set.add(1);
				TestServerMessage lMessageToSend = new TestServerMessage("ILoveTitties", set);
				Gson gson = new Gson();
				String json = gson.toJson(lMessageToSend);
				while(true){
					//System.out.println("[Client] Enter message to send.");
					//String lMessageToSend = reader.next();
					
					aDataOutputStream.writeUTF(json);
					sleep(10000);
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
					System.out.println("[Client] Message received: " + lMessageBeingRead);
				}
			}
			catch(Exception e){
				System.out.println("[Client] Error sending message.");
				e.printStackTrace();
			}
			
		}
	}
	public static void main(String[] args) {
		Client lClient = new Client();
	}
}
