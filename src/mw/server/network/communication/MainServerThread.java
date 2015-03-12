/**
 * @author Charlie Bloomfield
 * Feb 19, 2015
 */

package mw.server.network.communication;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Properties;

import mw.filesystem.ProjectFolder;
import mw.server.network.mappers.ClientChannelMapper;

/**
 * Listens to a port and sets up ClientChannels on unique sockets.
 */
public class MainServerThread extends Thread{
	private int PORT_NUMBER;
	private ServerSocket aServerSocket;
	
	/**
	 * Loads port number from config file
	 */
	public MainServerThread() {
		Properties lProperties = new Properties();
		InputStream lInputStream = null;
		
		//parse port number from config file
		try{
			lInputStream = new FileInputStream(ProjectFolder.getPath() + "config.properties");
			lProperties.load(lInputStream);
			PORT_NUMBER = Integer.parseInt(lProperties.getProperty("serverport"));
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (lInputStream != null) {
				try {
					lInputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * Opens a server socket for MainServer to listen to. When a client
	 * requests a connection, a new thread is created and communicates over
	 * a unique clientSocket.
	 */
	@Override
	public void run(){
		Socket lClientSocket; //temporarily stores the socket of the requesting client
		
		try {
			aServerSocket = new ServerSocket(PORT_NUMBER);
			
			while(true){
				System.out.println("[Server] Waiting on client to connect.");
				lClientSocket = aServerSocket.accept();
				System.out.println("[Server] Connected to " + lClientSocket.getLocalAddress());
				
				ClientChannel lClientChannel = new ClientChannel(lClientSocket);
				ClientChannelMapper.getInstance().putChannel(lClientChannel); //NOT THREADSAFE	
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
