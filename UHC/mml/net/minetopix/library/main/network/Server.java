package net.minetopix.library.main.network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import net.minetopix.library.main.network.listener.ServerListener;
import net.minetopix.library.main.network.listener.ServerRecievedMessageEvent;

public class Server{
	
	//Server part
	private ServerSocket serverSocket = null;
	private Socket connectedClient = null;
	private Server localServer = null;	
	private Server connected = null;	
	
	//Client
	private Socket client = null;

	//Values
	private boolean running = false;
	
	private String ip;
	private int port;
	
	private ArrayList<ServerListener> allListener = new ArrayList<>();
	
	public Server(String ipAdress , int port) {
		this.ip = ipAdress;
		this.port = port;
		
		running = true;
		localServer = this;
	}
	
	public void close() {
		running = false;
		try {
			serverSocket.close();
			client.close();
		} catch (Exception e) {
		}
	}
	/*
	 * ########### Server Part
	 */
	public void startListening() {
		try {
			this.serverSocket = new ServerSocket(port);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				while(running) {
					try {
						connectedClient = serverSocket.accept();
						
						DataInputStream in = new DataInputStream(connectedClient.getInputStream());
						
						while(!connectedClient.isClosed()) {
							String channel = in.readUTF();	
							String message = in.readUTF();
							
							ListenerFactory.performListener(localServer, new ServerRecievedMessageEvent(channel, message, connectedClient));
						}
						
					} catch (Exception e) {
						
					}
				}
			}
		}).start();
	}
	
	/*
	 * ########### Client Part
	 */
	
	public void connect(Server toConnect) {
		try {
			client = new Socket(toConnect.ip , toConnect.port);
			connected = toConnect;
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void sendMessage(String msg, String channel) {
		if(client == null || connected == null) {
			System.out.println("NO CLIENT CONNECTED");
			return;
		}
		
		try {
			DataOutputStream out = new DataOutputStream(client.getOutputStream());
			out.writeUTF(channel);
			out.writeUTF(msg);
			
			out.flush();
			
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void disconnect() {
		try {
			client.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		client = null;
		connected = null;
	}
	
	public Server getConnectedServer() {
		return connected;
	}
	
	public boolean isConnectedWithServer() {
		return client != null;
	}
	
	public void addListener(ServerListener listener) {
		if(!allListener.contains(listener))  {
			allListener.add(listener);
		}
	}
	
	public ArrayList<ServerListener> getAllListeners() {
		return allListener;
	}

	
}
