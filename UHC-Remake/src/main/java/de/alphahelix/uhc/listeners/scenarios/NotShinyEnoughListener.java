package de.alphahelix.uhc.listeners.scenarios;

import de.alphahelix.alphaapi.listener.SimpleListener;
import de.alphahelix.uhc.enums.Scenarios;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.CraftItemEvent;

public class NotShinyEnoughListener extends SimpleListener {

    @EventHandler
    public void onCraft(CraftItemEvent e) {
        if (e.isCancelled())
            return;
        if (!Scenarios.isPlayedAndEnabled(Scenarios.NOT_SHINY_ENOUGH))
            return;

        if (e.getRecipe().getResult().getType().equals(Material.ENCHANTMENT_TABLE)
                || e.getRecipe().getResult().getType().equals(Material.ANVIL))
            e.setCancelled(true);
    }

}
