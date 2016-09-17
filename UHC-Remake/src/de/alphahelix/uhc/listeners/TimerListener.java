package de.alphahelix.uhc.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;

import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.instances.SimpleListener;

public class TimerListener extends SimpleListener {

	public TimerListener(UHC uhc) {
		super(uhc);
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		getRegister().getLobbyTimer().startLobbyCountdown();
	}
}
