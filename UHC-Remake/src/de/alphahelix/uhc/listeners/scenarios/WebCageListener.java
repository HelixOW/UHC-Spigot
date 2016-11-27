package de.alphahelix.uhc.listeners.scenarios;

import de.alphahelix.uhc.Scenarios;
import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.instances.SimpleListener;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;

/**
 * Created by AlphaHelixDev.
 */
public class WebCageListener extends SimpleListener {

    public WebCageListener(UHC uhc) {
        super(uhc);
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        if(!scenarioCheck(Scenarios.WEB_CAGE)) return;

        Location m = e.getEntity().getLocation();

        for(int xz = 0; xz < 5; xz++) {
            m.getWorld().getBlockAt(m.getBlockX() + xz, m.getBlockY() + xz, m.getBlockZ() + xz);
            m.getWorld().getBlockAt(m.getBlockX() - xz, m.getBlockY(), m.getBlockZ() - xz);
        }
    }
}
