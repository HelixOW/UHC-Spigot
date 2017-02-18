package de.alphahelix.uhc.listeners.scenarios;

import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.enums.Scenarios;
import de.alphahelix.uhc.instances.SimpleListener;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.CraftItemEvent;

public class NotShinyEnoughListener extends SimpleListener {

    public NotShinyEnoughListener(UHC uhc) {
        super(uhc);
    }

    @EventHandler
    public void onCraft(CraftItemEvent e) {
        if (e.isCancelled())
            return;
        if (!scenarioCheck(Scenarios.NOT_SHINY_ENOUGH))
            return;

        if (e.getRecipe().getResult().getType().equals(Material.ENCHANTMENT_TABLE)
                || e.getRecipe().getResult().getType().equals(Material.ANVIL))
            e.setCancelled(true);
    }

}
