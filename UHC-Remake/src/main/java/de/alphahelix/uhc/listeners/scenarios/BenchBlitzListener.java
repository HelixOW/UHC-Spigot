package de.alphahelix.uhc.listeners.scenarios;

import de.alphahelix.alphalibary.listener.SimpleListener;
import de.alphahelix.uhc.enums.Scenarios;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.CraftItemEvent;

import java.util.ArrayList;

public class BenchBlitzListener extends SimpleListener {

    private ArrayList<String> hasWorkbench = new ArrayList<>();

    @EventHandler
    public void onCraft(CraftItemEvent e) {
        if (e.isCancelled()) return;
        if (!Scenarios.isPlayedAndEnabled(Scenarios.BENCH_BLITZ)) return;
        if (e.getRecipe().getResult().getType().equals(Material.WORKBENCH))
            if (hasWorkbench.contains(e.getWhoClicked().getName()))
                e.setCancelled(true);
            else
                hasWorkbench.add(e.getWhoClicked().getName());
    }
}
