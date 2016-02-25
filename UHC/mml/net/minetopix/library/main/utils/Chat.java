package net.minetopix.library.main.utils;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Chat {

	private String prefix = "";
	
	public Chat(String prefix) {
		this.prefix = prefix;
	}
	
	public void send(Player p , String msg) {
		p.sendMessage(prefix + msg);
	}
	
	public void broadCast(String msg) {
		for(Player p : Bukkit.getOnlinePlayers()) {
			send(p, msg);
		}
	}
	
	public void broadCast(ArrayList<Player> toBroadCast, String msg) {
		for(Player p : toBroadCast) {
			send(p, msg);
		}
	}
	
	public String getPrefix() {
		return prefix;
	}
	
}
