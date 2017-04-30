package de.alphahelix.uhc.listeners.scenarios;

import de.alphahelix.alphalibary.listener.SimpleListener;
import de.alphahelix.uhc.enums.Scenarios;
import org.bukkit.Material;
import org.bukkit.World.Environment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockPlaceEvent;

import java.util.ArrayList;

public class EnderDanceListener extends SimpleListener {

    private ArrayList<String> alreadyHealed = new ArrayList<>();

    @EventHandler
    public void onPlace(BlockPlaceEvent e) {
        if (e.isCancelled())
            return;
        if (!Scenarios.isPlayedAndEnabled(Scenarios.ENDER_DANCE))
            return;

        if (e.getBlockPlaced().getType().equals(Material.JUKEBOX)) {
            if (alreadyHealed.contains(e.getPlayer().getName()))
                return;
            if (!e.getBlock().getWorld().getEnvironment().equals(Environment.THE_END))
                return;
            e.setCancelled(true);
            alreadyHealed.add(e.getPlayer().getName());
            e.getPlayer().setHealth(e.getPlayer().getMaxHealth());
        }
    }
}
