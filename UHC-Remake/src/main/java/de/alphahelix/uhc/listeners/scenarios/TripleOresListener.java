package de.alphahelix.uhc.listeners.scenarios;

import de.alphahelix.alphalibary.listener.SimpleListener;
import de.alphahelix.uhc.enums.Scenarios;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

public class TripleOresListener extends SimpleListener {

    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        if (e.isCancelled()) return;
        if (!Scenarios.isPlayedAndEnabled(Scenarios.TRIPLE_ORES)) return;

        if (e.getBlock().getType().name().contains("ORE")) {
            e.getBlock().getWorld().dropItemNaturally(e.getBlock().getLocation(), e.getBlock().getDrops().toArray(new ItemStack[e.getBlock().getDrops().size()])[0]);
            e.getBlock().getWorld().dropItemNaturally(e.getBlock().getLocation(), e.getBlock().getDrops().toArray(new ItemStack[e.getBlock().getDrops().size()])[0]);
        } else if (e.getBlock().getType().equals(Material.GRAVEL)) {
            e.getBlock().getWorld().dropItemNaturally(e.getBlock().getLocation(), e.getBlock().getDrops().toArray(new ItemStack[e.getBlock().getDrops().size()])[0]);
            e.getBlock().getWorld().dropItemNaturally(e.getBlock().getLocation(), e.getBlock().getDrops().toArray(new ItemStack[e.getBlock().getDrops().size()])[0]);
        }
    }
}
