package de.alphahelix.uhc.listeners.scenarios;

import de.alphahelix.alphalibary.listener.SimpleListener;
import de.alphahelix.uhc.enums.Scenarios;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.CraftItemEvent;

public class LightsOutListener extends SimpleListener {

    @EventHandler
    public void onCraft(CraftItemEvent e) {
        if (e.isCancelled())
            return;
        if (!Scenarios.isPlayedAndEnabled(Scenarios.LIGHTS_OUT))
            return;

        if (e.getRecipe().getResult().getType().equals(Material.TORCH))
            e.setCancelled(true);
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent e) {
        if (e.isCancelled())
            return;
        if (!Scenarios.isPlayedAndEnabled(Scenarios.LIGHTS_OUT))
            return;

        if (e.getBlockPlaced().getType().equals(Material.TORCH))
            e.setCancelled(true);
    }
}
