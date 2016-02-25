package net.minetopix.library.main.network.listener;

import java.net.Socket;

public class ServerRecievedMessageEvent implements ServerListener{

	private String message,channel;
	private Socket sender;
	
	public ServerRecievedMessageEvent(String channel , String message , Socket sender) {
		this.message = message;
		this.channel = channel;
		this.sender = sender;
	}
	
	public String getChannel() {
		return channel;
	}
	public String getMessage() {
		return message;
	}
	public Socket getSender() {
		return sender;
	}
	
}
