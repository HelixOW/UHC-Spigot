package de.alphahelix.uhc.listeners.scenarios;

import de.alphahelix.uhc.Scenarios;
import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.instances.SimpleListener;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class SwitcherooListener extends SimpleListener {

	public SwitcherooListener(UHC uhc) {
		super(uhc);
	}

	@EventHandler
	public void onEntityDamageByEntity(EntityDamageByEntityEvent e) {
		if(e.isCancelled()) return;
		if(!scenarioCheck(Scenarios.SWITCHEROO)) return;
		if (e.getEntity() instanceof Player) {
			Player p = (Player) e.getEntity();

			if (e.getDamager() instanceof Projectile) {
				Projectile proj = (Projectile) e.getDamager();

				if (proj.getShooter() instanceof Player) {
					Player shooter = (Player) proj.getShooter();

					Location loc1 = p.getLocation();
					Location loc2 = shooter.getLocation();

					shooter.teleport(loc1);
					p.teleport(loc2);
				}
			}
		}
	}

}
