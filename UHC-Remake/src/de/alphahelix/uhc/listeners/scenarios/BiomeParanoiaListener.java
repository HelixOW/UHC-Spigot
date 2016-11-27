package de.alphahelix.uhc.listeners.scenarios;

import de.alphahelix.uhc.Scenarios;
import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.instances.SimpleListener;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.Random;

public class BiomeParanoiaListener extends SimpleListener {
	

	public BiomeParanoiaListener(UHC uhc) {
		super(uhc);
	}
	
	@EventHandler
	public void onMove(PlayerMoveEvent e) {
		if(e.isCancelled()) return;
		if(!scenarioCheck(Scenarios.BIOME_PARANOIA)) return;
		if(e.getTo().getBlock().getBiome().equals(e.getFrom().getBlock().getBiome())) return;
		
		e.getPlayer().setPlayerListName(getRandomColor() + e.getPlayer().getDisplayName());
	}
	
	private ChatColor getRandomColor() {
		int index = new Random().nextInt(ChatColor.values().length);
		return ChatColor.values()[index];
	}
}
