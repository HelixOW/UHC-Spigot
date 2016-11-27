package de.alphahelix.uhc.listeners.scenarios;

import de.alphahelix.uhc.Scenarios;
import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.instances.SimpleListener;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.CraftItemEvent;

public class LightsOutListener extends SimpleListener {

	public LightsOutListener(UHC uhc) {
		super(uhc);
	}

	@EventHandler
	public void onCraft(CraftItemEvent e) {
		if (e.isCancelled())
			return;
		if (!scenarioCheck(Scenarios.LIGHTS_OUT))
			return;

		if (e.getRecipe().getResult().getType().equals(Material.TORCH))
			e.setCancelled(true);
	}

	@EventHandler
	public void onPlace(BlockPlaceEvent e) {
		if (e.isCancelled())
			return;
		if (!scenarioCheck(Scenarios.LIGHTS_OUT))
			return;

		if (e.getBlockPlaced().getType().equals(Material.TORCH))
			e.setCancelled(true);
	}
}
