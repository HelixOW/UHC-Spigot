package de.alphahelix.uhc.listeners.scenarios;

import de.alphahelix.alphalibary.listener.SimpleListener;
import de.alphahelix.uhc.enums.Scenarios;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

public class GunsNRosesListener extends SimpleListener {

    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        if (e.isCancelled())
            return;
        if (!Scenarios.isPlayedAndEnabled(Scenarios.GUNS_N_ROSES))
            return;

        if (e.getBlock().getType().equals(Material.RED_ROSE) || e.getBlock().getType().equals(Material.DOUBLE_PLANT)) {
            if (Math.random() < 0.02) {
                e.getBlock().getWorld().dropItemNaturally(e.getBlock().getLocation(), new ItemStack(Material.BOW));
            }
        }

        if (e.getBlock().getType().equals(Material.RED_ROSE)) {
            e.getBlock().getWorld().dropItemNaturally(e.getBlock().getLocation(), new ItemStack(Material.ARROW));
        } else if (e.getBlock().getType().equals(Material.DOUBLE_PLANT)) {
            e.getBlock().getWorld().dropItemNaturally(e.getBlock().getLocation(), new ItemStack(Material.ARROW, 4));
        }
    }
}
