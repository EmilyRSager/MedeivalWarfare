package mw.server.network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MainServer {
	private static final int PORT_NUMBER = 6666;
	ServerSocket aServerSocket;
	Socket aClientSocket;
	ClientList aClientList;
	
	public MainServer(){
		aClientList = new ClientList();
	}
	
	/*
	 * opens a server socket for MainServer to listen to. When a client
	 * requests a connection, a new thread is created and communicates over
	 * a unique clientSocket.
	 */
	public void listen(){
		
		try {
			aServerSocket = new ServerSocket(PORT_NUMBER);
			
			while(true){
				System.out.println("Waiting on client to connect.\n");
				aClientSocket = aServerSocket.accept();
				System.out.println("Server connected to " + aClientSocket.getLocalAddress());
				
				Client lClient = new Client(aClientSocket);
				aClientList.add(lClient);
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
