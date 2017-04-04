package de.alphahelix.uhc.listeners.scenarios;

import de.alphahelix.alphaapi.listener.SimpleListener;
import de.alphahelix.uhc.enums.Scenarios;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntitySpawnEvent;

public class HorselessListener extends SimpleListener {

    @EventHandler
    public void onSpawn(CreatureSpawnEvent e) {
        if (e.isCancelled()) return;
        if (!Scenarios.isPlayedAndEnabled(Scenarios.HORSELESS)) return;

        if (e.getEntityType() == EntityType.HORSE) e.setCancelled(true);
    }

    @EventHandler
    public void onSpawn(EntitySpawnEvent e) {
        if (e.isCancelled()) return;
        if (!Scenarios.isPlayedAndEnabled(Scenarios.HORSELESS)) return;

        if (e.getEntityType() == EntityType.HORSE) e.setCancelled(true);
    }
}
