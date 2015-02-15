package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MainServer {
	private static final int PORT_NUMBER = 6666;
	ServerSocket serverSocket;
	Socket clientSocket;
	
	public void listen(){
		
		try {
			serverSocket = new ServerSocket(PORT_NUMBER);
			
			while(true){
				System.out.println("Waiting on client to connect.\n");
				clientSocket = serverSocket.accept();
				System.out.println("Server connected to " + clientSocket.getLocalAddress());
				
				Thread clientCommunicatorThread = new Thread(new ClientCommunicator(clientSocket));
				clientCommunicatorThread.start();
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		MainServer listener = new MainServer();
		listener.listen();
	}
}
