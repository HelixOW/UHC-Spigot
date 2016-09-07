package de.alphahelix.uhc.util;

import org.bukkit.entity.Player;

import de.alphahelix.uhc.UHC;
import net.md_5.bungee.api.chat.TranslatableComponent;

public class TranslatorUtil extends Util{

	public TranslatorUtil(UHC uhc) {
		super(uhc);
	}
	
	public void sendMessage(String message, Player p) {
		TranslatableComponent msg = new TranslatableComponent(message);
		p.spigot().sendMessage(msg);
	}

}
