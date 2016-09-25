package de.alphahelix.uhc.listeners.scenarios;

import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import de.alphahelix.uhc.Scenarios;
import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.instances.SimpleListener;

public class LongshotListener extends SimpleListener {

	public LongshotListener(UHC uhc) {
		super(uhc);
	}
	
	@EventHandler
	public void onEntityDamageByEntity(EntityDamageByEntityEvent e) {
		if (e.isCancelled())
			return;
		if (!scenarioCheck(Scenarios.LONGSHOTS))
			return;
		if (!(e.getDamager() instanceof Arrow) || !(e.getEntity() instanceof Player)) {
			return;
		}

		Player p = (Player) e.getEntity();
		Arrow damager = (Arrow) e.getDamager();

		if (!(damager.getShooter() instanceof Player)) {
			return;
		}

		Player k = (Player) damager.getShooter();
		double distance = k.getLocation().distance(p.getLocation());

		if (distance >= 75) {
			p.damage(3);
			if (k.getHealth() > 18)
				k.setHealth(20);
			else k.setHealth(k.getHealth() + 2.0);
		}
	}
}
