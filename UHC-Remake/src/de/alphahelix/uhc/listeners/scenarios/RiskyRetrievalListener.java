package de.alphahelix.uhc.listeners.scenarios;

import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import de.alphahelix.uhc.Scenarios;
import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.events.timers.LobbyEndEvent;
import de.alphahelix.uhc.instances.SimpleListener;

public class RiskyRetrievalListener extends SimpleListener {

	private Chest rr;

	public RiskyRetrievalListener(UHC uhc) {
		super(uhc);
	}

	@EventHandler
	public void onEnd(LobbyEndEvent e) {
		if (!scenarioCheck(Scenarios.RISKY_RETRIEVAL))
			return;

		getRegister().getLocationsFile().getArena().getBlock().setType(Material.CHEST);
		rr = (Chest) getRegister().getLocationsFile().getArena().getBlock().getState();
	}

	@EventHandler
	public void onBreak(BlockBreakEvent e) {
		if (e.isCancelled())
			return;
		if (!scenarioCheck(Scenarios.RISKY_RETRIEVAL))
			return;

		if (e.getBlock().getType().equals(Material.GOLD_ORE) || e.getBlock().getType().equals(Material.DIAMOND_ORE)) {
			rr.getBlockInventory()
					.addItem(e.getBlock().getDrops().toArray(new ItemStack[e.getBlock().getDrops().size()]));
			e.setCancelled(true);
			e.getBlock().setType(Material.AIR);
		}

		else if (e.getBlock().getX() == rr.getX() && e.getBlock().getY() == rr.getY()
				&& e.getBlock().getZ() == rr.getZ())
			e.setCancelled(true);
	}
}
