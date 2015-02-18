package mw.server.network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MainServer{
	private static final int PORT_NUMBER = 6666;
	ServerSocket aServerSocket;
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
		Socket lClientSocket; //temporarily stores the socket of the requesting client
		
		try {
			aServerSocket = new ServerSocket(PORT_NUMBER);
			
			while(true){
				System.out.println("Waiting on client to connect.\n");
				lClientSocket = aServerSocket.accept();
				System.out.println("Server connected to " + lClientSocket.getLocalAddress());
				
				ServerClient lServerClient = new ServerClient(lClientSocket);
				//aClientList.add(lClient);
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args){
		MainServer m = new MainServer();
		m.listen();
	}
}
