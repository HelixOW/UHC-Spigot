package de.alphahelix.uhc.listeners.scenarios;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.World.Environment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockPlaceEvent;

import de.alphahelix.uhc.Scenarios;
import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.instances.SimpleListener;

public class EnderDanceListener extends SimpleListener {

	private ArrayList<String> alreadyHealed = new ArrayList<>();

	public EnderDanceListener(UHC uhc) {
		super(uhc);
	}

	@EventHandler
	public void onPlace(BlockPlaceEvent e) {
		if (e.isCancelled())
			return;
		if (!scenarioCheck(Scenarios.ENDER_DANCE))
			return;

		if (e.getBlockPlaced().getType().equals(Material.JUKEBOX)) {
			if (alreadyHealed.contains(e.getPlayer().getName()))
				return;
			if (!e.getBlock().getWorld().getEnvironment().equals(Environment.THE_END))
				return;
			e.setCancelled(true);
			alreadyHealed.add(e.getPlayer().getName());
			e.getPlayer().setHealth(e.getPlayer().getMaxHealth());
		}
	}
}
