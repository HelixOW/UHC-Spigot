package de.alphahelix.uhc.listeners.scenarios;

import de.alphahelix.alphaapi.listener.SimpleListener;
import de.alphahelix.uhc.enums.Scenarios;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Spider;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntitySpawnEvent;

public class EightLeggedFreaksListener extends SimpleListener {

    @EventHandler
    public void onSpawn(EntitySpawnEvent e) {
        if (e.isCancelled())
            return;
        if (!Scenarios.isPlayedAndEnabled(Scenarios.EIGHT_LEGGED_FREAKS))
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
        if (!Scenarios.isPlayedAndEnabled(Scenarios.EIGHT_LEGGED_FREAKS))
            return;

        if (e.getEntity() instanceof Monster && !e.getEntity().getType().equals(EntityType.SPIDER)) {
            e.getEntity().getWorld().spawn(e.getLocation(), Spider.class);
            e.setCancelled(true);
        }
    }
}
