package de.alphahelix.uhc.listeners.scenarios;

import de.alphahelix.alphalibary.listener.SimpleListener;
import de.alphahelix.uhc.enums.Scenarios;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;

public class WebCageListener extends SimpleListener {

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        if (!Scenarios.isPlayedAndEnabled(Scenarios.WEB_CAGE)) return;

        Location m = e.getEntity().getLocation();

        for (int xz = 0; xz < 5; xz++) {
            m.getWorld().getBlockAt(m.getBlockX() + xz, m.getBlockY() + xz, m.getBlockZ() + xz);
            m.getWorld().getBlockAt(m.getBlockX() - xz, m.getBlockY(), m.getBlockZ() - xz);
        }
    }
}
