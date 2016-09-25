package de.alphahelix.uhc.listeners.scenarios;

import java.util.Random;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import de.alphahelix.uhc.Scenarios;
import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.events.timers.LobbyEndEvent;
import de.alphahelix.uhc.instances.SimpleListener;

public class DiamondlessListener extends SimpleListener {

	public DiamondlessListener(UHC uhc) {
		super(uhc);
	}

	@EventHandler
	public void onEnd(LobbyEndEvent e) {
		if (!scenarioCheck(Scenarios.DIAMONDLESS))
			return;

		getRegister().getLocationsFile().getArena().getBlock().setType(Material.ENCHANTMENT_TABLE);
	}

	@EventHandler
	public void onBreak(BlockBreakEvent e) {
		if (e.isCancelled())
			return;
		if (!scenarioCheck(Scenarios.DIAMONDLESS))
			return;

		int r = new Random().nextInt(10) + 1;

		if (e.getBlock().getType().equals(Material.DIAMOND_ORE)) {
			e.setCancelled(true);
			e.getBlock().setType(Material.AIR);
			if (new Random().nextInt(2) + 1 == 1) {
				e.getBlock().getWorld().dropItemNaturally(e.getBlock().getLocation(), new ItemStack(Material.ROTTEN_FLESH));
			}
		}

		else if (r == 1 && e.getBlock().getType().equals(Material.GOLD_ORE)) {
			e.setCancelled(true);
			e.getBlock().setType(Material.AIR);
		}
	}
}
