package de.alphahelix.uhc.listeners.scenarios;

import de.alphahelix.alphalibary.listener.SimpleListener;
import de.alphahelix.uhc.enums.Scenarios;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.CraftItemEvent;

public class CaptainsListener extends SimpleListener {

    @EventHandler
    public void onCraft(CraftItemEvent e) {
        if (e.isCancelled())
            return;
        if (!Scenarios.isPlayedAndEnabled(Scenarios.CAPTAINS))
            return;

        if (e.getRecipe().getResult().getType().name().contains("SWORD")
                && !e.getRecipe().getResult().getType().equals(Material.IRON_SWORD))
            e.setCancelled(true);
    }
}
