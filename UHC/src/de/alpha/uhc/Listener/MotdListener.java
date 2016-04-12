package de.alpha.uhc.Listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

import de.alpha.uhc.GState;

public class MotdListener implements Listener {
	
	private static String lobby;
	private static String grace;
	private static String ingame;
	private static String restart;
	
	
	
	public static  void setLobby(String lobby) {
		MotdListener.lobby = lobby;
	}

	public static  void setGrace(String grace) {
		MotdListener.grace = grace;
	}

	public static  void setIngame(String ingame) {
		MotdListener.ingame = ingame;
	}

	public static  void setRestart(String restart) {
		MotdListener.restart = restart;
	}

	public static  void setCustommotd(boolean custommotd) {
		MotdListener.custommotd = custommotd;
	}

	public static boolean custommotd;
	
	@EventHandler
	public void onPing(ServerListPingEvent e) {
		
		if(custommotd == false) return;
		
		if(GState.isState(GState.LOBBY)) {
			e.setMotd(lobby);
		} else if(GState.isState(GState.INGAME)) {
			e.setMotd(ingame);
		} else if(GState.isState(GState.RESTART)) {
			e.setMotd(restart);
		} else if(GState.isState(GState.GRACE)) {
			e.setMotd(grace);
		}
		
	}
}
