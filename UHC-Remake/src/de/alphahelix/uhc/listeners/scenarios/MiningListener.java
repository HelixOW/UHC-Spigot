package de.alphahelix.uhc.listeners.scenarios;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;

import de.alphahelix.uhc.GState;
import de.alphahelix.uhc.Scenarios;
import de.alphahelix.uhc.UHC;

public class MiningListener extends ScenarioListener {

	private static ArrayList<String> miners = new ArrayList<>();

	public MiningListener(UHC uhc) {
		super(uhc);
	}

	private boolean hasMined(Player p) {
		return miners.contains(p.getName());
	}

	@EventHandler
	public void onMine(BlockBreakEvent e) {
		Player p = e.getPlayer();
		
		System.out.println("ohoho");
		
		if(e.isCancelled()) return;

		if (GState.isState(GState.LOBBY) || GState.isState(GState.END))
			return;
		
		if(!Scenarios.isScenario(Scenarios.HALF_ORES)) return;
		
		if(!e.getBlock().getType().name().contains("ORE")) return;
		
		if (hasMined(p)) {
			miners.remove(p.getName());
		} else {
			miners.add(p.getName());
			e.setCancelled(true);
			e.getBlock().setType(Material.AIR);
		}
	}
}
