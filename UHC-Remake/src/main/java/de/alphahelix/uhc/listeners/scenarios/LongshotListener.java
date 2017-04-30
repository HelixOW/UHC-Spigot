package de.alphahelix.uhc.listeners.scenarios;

import de.alphahelix.alphalibary.listener.SimpleListener;
import de.alphahelix.uhc.enums.Scenarios;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class LongshotListener extends SimpleListener {

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent e) {
        if (e.isCancelled())
            return;
        if (!Scenarios.isPlayedAndEnabled(Scenarios.LONGSHOTS) || Scenarios.isPlayedAndEnabled(Scenarios.REWARDING_LONGSHOTS))
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
