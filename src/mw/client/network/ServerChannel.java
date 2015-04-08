/**
 * @author Charlie Bloomfield
 * Feb 20, 2015
 */

package mw.client.network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.Properties;

import mw.filesystem.ProjectFolder;
import mw.shared.servercommands.AbstractServerCommand;

public class ServerChannel extends Thread{
	private String SERVER_IP_ADDRESS;
	private int PORT_NUMBER;

	private Socket aSocket;
	
	private WriterThread aWriterThread;
	private ReaderThread aReaderThread; //no need for reference to reader thread

	/**
	 * Constructor. Initializes two threads to read data from and write data to the server.
	 * @param none
	 */
	public ServerChannel(){
		Properties lProperties = new Properties();
		InputStream lInputStream = null;
		
		//parse server ip address and port number from config file
		try{
			lInputStream = new FileInputStream(ProjectFolder.getPath() + "config.properties");
			lProperties.load(lInputStream);
			PORT_NUMBER = Integer.parseInt(lProperties.getProperty("serverport"));
			SERVER_IP_ADDRESS = lProperties.getProperty("serveripaddress");
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
		
		//TODO split up class construction and socket initialization
		try
		{	
			System.out.println("inside tru");
			
			aSocket = new Socket(SERVER_IP_ADDRESS, PORT_NUMBER);
			
			System.out.println("[Client] Connecting to " + SERVER_IP_ADDRESS + " on PORT_NUMBER " + PORT_NUMBER + ".");
			System.out.println("[Client] Just connected to " + aSocket.getRemoteSocketAddress());
			
			aWriterThread = new WriterThread(
					new DataOutputStream(aSocket.getOutputStream()));
			aWriterThread.start();
			
			
			aReaderThread = new ReaderThread(
					new DataInputStream(aSocket.getInputStream()));
			aReaderThread.start();
				
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public void sendCommand(AbstractServerCommand pServerCommand){
		aWriterThread.sendCommand(pServerCommand);
	}
}
