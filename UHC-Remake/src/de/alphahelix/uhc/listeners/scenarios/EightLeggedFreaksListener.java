package de.alphahelix.uhc.listeners.scenarios;

import de.alphahelix.uhc.Scenarios;
import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.instances.SimpleListener;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Spider;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntitySpawnEvent;

public class EightLeggedFreaksListener extends SimpleListener {

	public EightLeggedFreaksListener(UHC uhc) {
		super(uhc);
	}

	@EventHandler
	public void onSpawn(EntitySpawnEvent e) {
		if (e.isCancelled())
			return;
		if (!scenarioCheck(Scenarios.EIGHT_LEGGED_FREAKS))
			return;

		if (e.getEntity() instanceof Monster && !e.getEntity().getType().equals(EntityType.SPIDER)) {
			e.getEntity().getWorld().spawn(e.getLocation(), Spider.class);
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void onSpawn(CreatureSpawnEvent e) {
		if (e.isCancelled())
			return;
		if (!scenarioCheck(Scenarios.EIGHT_LEGGED_FREAKS))
			return;
		
		if (e.getEntity() instanceof Monster && !e.getEntity().getType().equals(EntityType.SPIDER)) {
			e.getEntity().getWorld().spawn(e.getLocation(), Spider.class);
			e.setCancelled(true);
		}
	}
}
