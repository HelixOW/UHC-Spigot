package de.alpha.uhc.Listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import de.alpha.uhc.Core;

public class ChatListener implements Listener {
	
	@EventHandler
	public void onChat(AsyncPlayerChatEvent e) {
		
		if(Core.getSpecs().contains(e.getPlayer())) {
			e.setCancelled(true);
			
			for(Player specs : Core.getSpecs()) {
				specs.sendMessage("§7[§4X§7] §c" + e.getPlayer().getDisplayName() + "§7: " + e.getMessage());
			}
		}
		
	}

}
