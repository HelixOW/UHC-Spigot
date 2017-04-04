package de.alphahelix.uhc.listeners.scenarios;

import de.alphahelix.alphaapi.listener.SimpleListener;
import de.alphahelix.uhc.enums.Scenarios;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

public class DoubleOrNothingListener extends SimpleListener {

    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        if (e.isCancelled())
            return;
        if (!Scenarios.isPlayedAndEnabled(Scenarios.DOUBLE_OR_NOTHING))
            return;

        if (e.getBlock().getType().equals(Material.IRON_ORE) || e.getBlock().getType().equals(Material.GOLD_ORE)
                || e.getBlock().getType().equals(Material.DIAMOND_ORE)) {
            if (Math.random() < 1) {
                ItemStack drop = e.getBlock().getDrops().toArray(new ItemStack[e.getBlock().getDrops().size()])[0];
                e.getBlock().getWorld().dropItemNaturally(e.getBlock().getLocation(), drop);
            } else {
                e.setCancelled(true);
                e.getBlock().setType(Material.AIR);
            }
        }
    }
}
