package de.alphahelix.uhc.listeners.scenarios;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;

import de.alphahelix.uhc.Scenarios;
import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.instances.SimpleListener;

public class CompensationListener extends SimpleListener {

	public CompensationListener(UHC uhc) {
		super(uhc);
	}

	@EventHandler
	public void onDeath(PlayerDeathEvent e) {
		if (!scenarioCheck(Scenarios.COMPENSATION))
			return;
		if (!(e.getEntity().getKiller() instanceof Player))
			return;
		
		e.getEntity().getKiller().setMaxHealth(e.getEntity().getKiller().getMaxHealth() + (e.getEntity().getMaxHealth() / 4));
		e.getEntity().getKiller().setHealth(e.getEntity().getKiller().getMaxHealth());
	}
}
