package de.alphahelix.uhc.listeners.scenarios;

import de.alphahelix.uhc.Scenarios;
import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.instances.SimpleListener;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntitySpawnEvent;

/**
 * Created by AlphaHelixDev.
 */
public class HorselessListener extends SimpleListener {

    public HorselessListener(UHC uhc) {
        super(uhc);
    }

    @EventHandler
    public void onSpawn(CreatureSpawnEvent e) {
        if(e.isCancelled()) return;
        if(!scenarioCheck(Scenarios.HORSELESS)) return;

        if(e.getEntityType() == EntityType.HORSE) e.setCancelled(true);
    }

    @EventHandler
    public void onSpawn(EntitySpawnEvent e) {
        if(e.isCancelled()) return;
        if(!scenarioCheck(Scenarios.HORSELESS)) return;

        if(e.getEntityType() == EntityType.HORSE) e.setCancelled(true);
    }
}
