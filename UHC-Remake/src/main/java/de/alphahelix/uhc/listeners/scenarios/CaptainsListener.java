package de.alphahelix.uhc.listeners.scenarios;

import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.enums.Scenarios;
import de.alphahelix.uhc.instances.SimpleListener;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.CraftItemEvent;

public class CaptainsListener extends SimpleListener {

    public CaptainsListener(UHC uhc) {
        super(uhc);
    }

    @EventHandler
    public void onCraft(CraftItemEvent e) {
        if (e.isCancelled())
            return;
        if (!scenarioCheck(Scenarios.CAPTAINS))
            return;

        if (e.getRecipe().getResult().getType().name().contains("SWORD")
                && !e.getRecipe().getResult().getType().equals(Material.IRON_SWORD))
            e.setCancelled(true);
    }
}
