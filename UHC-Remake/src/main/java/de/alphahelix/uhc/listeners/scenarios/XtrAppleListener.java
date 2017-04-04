package de.alphahelix.uhc.listeners.scenarios;

import de.alphahelix.alphaapi.listener.SimpleListener;
import de.alphahelix.uhc.enums.Scenarios;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.LeavesDecayEvent;
import org.bukkit.inventory.ItemStack;

public class XtrAppleListener extends SimpleListener {

    @EventHandler
    public void onDecay(LeavesDecayEvent e) {
        if (e.isCancelled())
            return;
        if (!Scenarios.isPlayedAndEnabled(Scenarios.XTR_APPLE))
            return;

        if (Math.random() < 0.4) {
            e.getBlock().getWorld().dropItemNaturally(e.getBlock().getLocation(), new ItemStack(Material.APPLE));
        }
    }

    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        if (e.isCancelled())
            return;
        if (!Scenarios.isPlayedAndEnabled(Scenarios.XTR_APPLE))
            return;

        if (!(e.getBlock().getType().equals(Material.LEAVES) || e.getBlock().getType().equals(Material.LEAVES_2)))
            return;

        if (Math.random() < 0.4) {
            e.getBlock().getWorld().dropItemNaturally(e.getBlock().getLocation(), new ItemStack(Material.APPLE));
        }
    }
}
