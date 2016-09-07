package de.alphahelix.uhc.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.util.SimpleListener;

public class ChatListener extends SimpleListener {

	public ChatListener(UHC uhc) {
		super(uhc);
	}
	
	@EventHandler
	public void onChat(AsyncPlayerChatEvent e) {
		e.setCancelled(true);
		for(Player all : Bukkit.getOnlinePlayers()) {
			getRegister().getTranslatorUtil().sendMessage(e.getMessage(), all);
		}
	}

}
